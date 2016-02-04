/*
 * Copyright (c) 2016. meresti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package meresti.linkviewer.core.security;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;

/**
 * Advisor to pick up annotations from annotated interface methods as well. Pointcuts with form "@annotation(SomeAnnotationType)" pick up methods only if they are annotated in the concrete class.
 */
public abstract class AbstractAnnotationBasedAdvisor<ADVISED_TARGET, ANNOTATION extends Annotation, RETURN_VALUE_TYPE> extends AbstractPointcutAdvisor {

    private static final long serialVersionUID = -5893447641475848757L;

    private final Class<? extends ADVISED_TARGET> advisedTargetClass;
    private final Class<? extends ANNOTATION> annotationType;
    private final Class<? extends RETURN_VALUE_TYPE> returnValueType;

    protected AbstractAnnotationBasedAdvisor(final Class<? extends ADVISED_TARGET> advisedTargetClass, final Class<? extends ANNOTATION> annotationType, final Class<? extends RETURN_VALUE_TYPE> returnValueType) {
        this.advisedTargetClass = advisedTargetClass;
        this.annotationType = annotationType;
        this.returnValueType = returnValueType;
    }

    @Override
    public Pointcut getPointcut() {
        return new StaticMethodMatcherPointcut() {
            @Override
            public boolean matches(final Method method, final Class<?> targetClass) {
                return advisedTargetClass.isAssignableFrom(targetClass) && AnnotationUtils.getAnnotation(method, annotationType) != null;
            }
        };
    }

    @Override
    public Advice getAdvice() {
        return new AnnotationBasedAdvice();
    }

    protected abstract void advice(final RETURN_VALUE_TYPE returnValue, final ANNOTATION annotation);

    protected void advice(final Collection<RETURN_VALUE_TYPE> returnValue, final ANNOTATION annotation) {
        for (final RETURN_VALUE_TYPE element : returnValue) {
            advice(element, annotation);
        }
    }

    private class AnnotationBasedAdvice implements MethodInterceptor {
        @Override
        public Object invoke(final MethodInvocation invocation) throws Throwable {
            final Object returnValue = invocation.proceed();

            final ANNOTATION annotation = AnnotationUtils.getAnnotation(invocation.getMethod(), annotationType);
            if (ClassUtils.isAssignableValue(returnValueType, returnValue)) {
                advice((RETURN_VALUE_TYPE) returnValue, annotation);
            } else if (returnValue instanceof Collection) {
                final Collection<?> collection = (Collection<?>) returnValue;
                if (ClassUtils.isAssignable(returnValueType, CollectionUtils.findCommonElementType(collection))) {
                    advice((Collection<RETURN_VALUE_TYPE>) collection, annotation);
                }
            }
            return returnValue;
        }
    }
}
