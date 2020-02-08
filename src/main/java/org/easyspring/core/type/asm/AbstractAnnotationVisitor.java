package org.easyspring.core.type.asm;

import org.springframework.asm.AnnotationVisitor;

public abstract class AbstractAnnotationVisitor implements AnnotationVisitor {

    public void visit(String s, Object o) {

    }

    public void visitEnum(String s, String s1, String s2) {

    }

    public AnnotationVisitor visitAnnotation(String s, String s1) {
        return null;
    }

    public AnnotationVisitor visitArray(String s) {
        return null;
    }

    public void visitEnd() {

    }
}
