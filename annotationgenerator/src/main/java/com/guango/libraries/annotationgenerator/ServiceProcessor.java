package com.guango.libraries.annotationgenerator;

import com.google.auto.service.AutoService;
import com.guango.libraries.annotations.ServiceImpl;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ServiceProcessor extends AbstractProcessor {

    private ProcessingEnvironment mProcessingEnvironment = null;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mProcessingEnvironment = processingEnv;
        mProcessingEnvironment.getMessager().printMessage(
                Diagnostic.Kind.WARNING,
                "Current element Hello"
        );
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(ServiceImpl.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> serviceImplElements = roundEnv.getElementsAnnotatedWith(ServiceImpl.class);

        mProcessingEnvironment.getMessager().printMessage(
                Diagnostic.Kind.WARNING,
                "Current element Hello" + serviceImplElements.size()
        );


        for (Element item : serviceImplElements) {
            String packageName = mProcessingEnvironment.getElementUtils().getPackageOf(item).getQualifiedName().toString();

            mProcessingEnvironment.getMessager().printMessage(
                    Diagnostic.Kind.WARNING,
                    "Current element" + item.toString()
            );

            TypeSpec typeBuilder = TypeSpec.classBuilder("NewClassName")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .build();

            JavaFile javaFile = JavaFile.builder(packageName, typeBuilder)
                    .addFileComment("Generated code from Mvp Helper. Do not modify!")
                    .build();
            try {
                javaFile.writeTo(mProcessingEnvironment.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
