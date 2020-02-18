package org.easyspring.aop.aspectj;

import org.aspectj.weaver.reflect.ReflectionWorld;
import org.aspectj.weaver.tools.*;
import org.easyspring.aop.MethodMatcher;
import org.easyspring.aop.Pointcut;
import org.easyspring.util.ClassUtils;
import org.easyspring.util.StringUtils;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tancunshi
 */
public class AspectJExpressionPointcut implements Pointcut, MethodMatcher {

    //使用AspectJ中的关于aop的抽象
    //支持的原语；原即为原子，所以原语就是组成语法的基本单位
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
    }

    //expression 是pointcut的表达式
    private String expression;
    //PointcutExpression 是对表达式的抽象
    private  PointcutExpression pointcutExpression;

    private ClassLoader pointcutClassLoader;

    public AspectJExpressionPointcut(String expression){
        this.expression = expression;
    }

    public MethodMatcher getMethodMatcher() {
        return this;
    }

    public String getExpression() {
        return this.expression;
    }

    public boolean matches(Method method) {

        checkReadyToMatch();

        ShadowMatch shadowMatch = getShadowMatch(method);

        if (shadowMatch.alwaysMatches()) {
            return true;
        }

        return false;
    }
    private ShadowMatch getShadowMatch(Method method) {
        //ShadowMatch是Method在 PointcutExpression上的投影
        ShadowMatch shadowMatch = null;
        try {
            shadowMatch = this.pointcutExpression.matchesMethodExecution(method);
        }
        catch (ReflectionWorld.ReflectionWorldException ex) {

            throw new RuntimeException("not implemented yet");
        }
        return shadowMatch;
    }

    private void checkReadyToMatch() {
        if (getExpression() == null) {
            throw new IllegalStateException("Must set property 'expression' before attempting to match");
        }
        if (this.pointcutExpression == null) {
            this.pointcutClassLoader = ClassUtils.getDefaultClassLoader();
            this.pointcutExpression = buildPointcutExpression(this.pointcutClassLoader);
        }
    }

    private PointcutExpression buildPointcutExpression(ClassLoader classLoader) {


        PointcutParser parser = PointcutParser
                .getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRIMITIVES, classLoader);

        return parser.parsePointcutExpression(replaceBooleanOperators(getExpression()),
                null, new PointcutParameter[0]);
    }

    private String replaceBooleanOperators(String pcExpr) {
        String result = StringUtils.replace(pcExpr, " and ", " && ");
        result = StringUtils.replace(result, " or ", " || ");
        result = StringUtils.replace(result, " not ", " ! ");
        return result;
    }
}
