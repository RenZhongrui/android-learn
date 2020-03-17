package com.ams.plugin

import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.android.build.gradle.AppExtension
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.apache.commons.io.IOUtils
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassVisitor
import org.objectweb.asm.ClassWriter

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * 学习博客地址：https://www.jianshu.com/p/16ed4d233fd1
 * 如何理解Transform api https://www.jianshu.com/p/37df81365edf
 * 1、java文件会先转化为class文件，然后在转化为dex文件。
 * 2、而通过Gradle插件提供的Transform API，可以在编译成dex文件之前得到class文件。
 * 3、得到class文件之后，便可以通过ASM对字节码进行修改，即可完成字节码插桩。
 */
class AMSTestPlugin extends Transform implements Plugin<Project> {

    @Override
    void apply(Project project) {
        //registerTransform
        System.out.println("registerTransform");
        def android = project.extensions.getByType(AppExtension)
        android.registerTransform(this)
    }

    // 给 transform 起个名字。 这个 name 并不是最终的名字， 在 TransformManager 中会对名字再处理
    @Override
    String getName() {
        return "AMSTestPlugin"
    }

    /**
     * transform 要处理的数据类型。
     * CLASSES 表示要处理编译后的字节码，可能是 jar 包也可能是目录
     * RESOURCES 表示处理标准的 java 资源
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * transform 的作用域
     * PROJECT_ONLY：只处理当前项目
     * SUB_PROJECTS：只处理子项目
     * PROJECT_LOCAL_DEPS：只处理当前项目的本地依赖,例如jar, aar
     * EXTERNAL_LIBRARIES：只处理外部的依赖库
     * PROVIDED_ONLY：只处理本地或远程以provided形式引入的依赖库
     * TESTED_CODE：测试代码
     * ContentType 和 Scopes 都返回集合，TransformManager 中封装了默认的几种集中类型
     */
    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    // 当前 Transform 是否支持增量编译
    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(@NonNull TransformInvocation transformInvocation) {
        println '--------------- com.ams.plugin.LifecyclePlugin visit start --------------- '
        def startTime = System.currentTimeMillis()
        Collection<TransformInput> inputs = transformInvocation.inputs
        TransformOutputProvider outputProvider = transformInvocation.outputProvider
        //删除之前的输出
        if (outputProvider != null)
            outputProvider.deleteAll()
        //遍历inputs
        inputs.each { TransformInput input ->
            //遍历directoryInputs
            input.directoryInputs.each { DirectoryInput directoryInput ->
                handleDirectoryInput(directoryInput, outputProvider)
            }

            //遍历jarInputs
            input.jarInputs.each { JarInput jarInput ->
                handleJarInputs(jarInput, outputProvider)
            }
        }
        def cost = (System.currentTimeMillis() - startTime) / 1000
        println '--------------- com.ams.plugin.LifecyclePlugin visit end --------------- '
        println "com.ams.plugin.LifecyclePlugin cost ： $cost s"
    }

    // 处理文件目录下的class文件
    static void handleDirectoryInput(DirectoryInput directoryInput, TransformOutputProvider outputProvider) {
        //是否是目录
        if (directoryInput.file.isDirectory()) {
            //列出目录所有文件（包含子文件夹，子文件夹内文件）
            directoryInput.file.eachFileRecurse { File file ->
                def name = file.name
                if (checkClassFile(name)) {
                    println '----------- deal with "class" file <' + name + '> -----------'
                    // ASM中的class读写方法
                    ClassReader classReader = new ClassReader(file.bytes)
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    // 实现自定义ClassVisitor
                    ClassVisitor cv = new AMSTestClassVisitor(classWriter)
                    classReader.accept(cv, ClassReader.EXPAND_FRAMES)
                    // 通过文件流写入到文件中
                    byte[] code = classWriter.toByteArray()
                    FileOutputStream fos = new FileOutputStream(
                            file.parentFile.absolutePath + File.separator + name)
                    fos.write(code)
                    fos.close()
                }
            }
        }
        //处理完输入文件之后，要把输出给下一个任务
        def dest = outputProvider.getContentLocation(directoryInput.name,
                directoryInput.contentTypes, directoryInput.scopes,
                Format.DIRECTORY)
        FileUtils.copyDirectory(directoryInput.file, dest)
    }

    // 处理Jar中的class文件
    static void handleJarInputs(JarInput jarInput, TransformOutputProvider outputProvider) {
        if (jarInput.file.getAbsolutePath().endsWith(".jar")) {
            //重名名输出文件,因为可能同名,会覆盖
            def jarName = jarInput.name
            def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
            if (jarName.endsWith(".jar")) {
                jarName = jarName.substring(0, jarName.length() - 4)
            }
            JarFile jarFile = new JarFile(jarInput.file)
            Enumeration enumeration = jarFile.entries()
            File tmpFile = new File(jarInput.file.getParent() + File.separator + "classes_temp.jar")
            //避免上次的缓存被重复插入
            if (tmpFile.exists()) {
                tmpFile.delete()
            }
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(tmpFile))
            //用于保存
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = jarFile.getInputStream(jarEntry)
                //插桩class
                if (checkClassFile(entryName)) {
                    //class文件处理
                    println '----------- deal with "jar" class file <' + entryName + '> -----------'
                    jarOutputStream.putNextEntry(zipEntry)
                    ClassReader classReader = new ClassReader(IOUtils.toByteArray(inputStream))
                    ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_MAXS)
                    ClassVisitor cv = new AMSTestClassVisitor(classWriter)
                    classReader.accept(cv, ClassReader.EXPAND_FRAMES)
                    byte[] code = classWriter.toByteArray()
                    jarOutputStream.write(code)
                } else {
                    jarOutputStream.putNextEntry(zipEntry)
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }
                jarOutputStream.closeEntry()
            }
            //结束
            jarOutputStream.close()
            jarFile.close()
            def dest = outputProvider.getContentLocation(jarName + md5Name,
                    jarInput.contentTypes, jarInput.scopes, Format.JAR)
            FileUtils.copyFile(tmpFile, dest)
            tmpFile.delete()
        }
    }

    // 检查class文件是否需要处理
    // androidx/fragment/app/FragmentActivity
    // android/support/v4/app/FragmentActivity.class
    static boolean checkClassFile(String name) {
        //只处理需要的class文件 FragmentActivity.class
        return (name.endsWith(".class") && !name.startsWith("R\$")
                && !"R.class".equals(name) && !"BuildConfig.class".equals(name)
                && "androidx/fragment/app/FragmentActivity.class".equals(name))
    }

}
