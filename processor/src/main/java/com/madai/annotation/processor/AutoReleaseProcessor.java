package com.madai.annotation.processor;


import com.google.auto.service.AutoService;
import com.madai.annotation.AutoRelease;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

/**
 * Created by Wind_Fantasy on 2017/7/24.
 */
@AutoService(Processor.class)
public class AutoReleaseProcessor extends AbstractProcessor {
    Filer mFiler;
    Messager mMessager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mMessager = processingEnvironment.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(AutoRelease.class);

        MethodSpec.Builder builder = MethodSpec.methodBuilder("doRelease")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID);

        for (Element element : elements) {
            builder.addStatement(((TypeElement) element.getEnclosingElement()).getQualifiedName() + "." + element.getSimpleName() + "()");
        }


        TypeSpec typeSpec = TypeSpec.classBuilder("ReleaseUtils")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(builder.build()).build();

        JavaFile javaFile = JavaFile.builder("com.madai.annotation", typeSpec)
                .build();
        try {
            javaFile.writeTo(mFiler);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(AutoRelease.class.getCanonicalName());
        return set;
    }

    private void note(CharSequence msg) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, msg);
    }

    private void error(Element element, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR,
                String.format(msg, args), element);
    }
}
