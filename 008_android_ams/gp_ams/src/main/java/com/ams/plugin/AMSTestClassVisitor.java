package com.ams.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AMSTestClassVisitor extends ClassVisitor implements Opcodes {

    private String mClassName;

    public AMSTestClassVisitor(ClassVisitor cv) {
        super(Opcodes.ASM5, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //System.out.println("AMSTestClassVisitor : visit -----> started ：" + name);
        // 获取类名
        this.mClassName = name;
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        //System.out.println("AMSTestClassVisitor : visitMethod : " + name);
        // 获取方法访问者
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        //匹配FragmentActivity
        // android/support/v4/app/FragmentActivity
        // androidx/fragment/app/FragmentActivity
        System.out.println("mClassName:"+mClassName);
        if ("androidx/fragment/app/FragmentActivity".equals(this.mClassName)) {
            // 匹配方法名
            if ("onCreate".equals(name) ) {
                //处理onCreate
                System.out.println("AMSTestClassVisitor : change method ----> " + name);
                // 自定义方法访问者
                return new AMSTestOnCreateMethodVisitor(mv);
            } else if ("onDestroy".equals(name)) {
                //处理onDestroy
                System.out.println("AMSTestClassVisitor : change method ----> " + name);
                return new AMSTestOnDestroyMethodVisitor(mv);
            }
        }
        return mv;
    }

    @Override
    public void visitEnd() {
        //System.out.println("AMSTestClassVisitor : visit -----> end");
        super.visitEnd();
    }
}
