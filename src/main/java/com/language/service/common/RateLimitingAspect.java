package com.language.service.common;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import com.language.service.exception.BusinessException;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitingAspect {
    private final Map<String, Map<String, Bucket>> bucketMap = new ConcurrentHashMap<>();
    @Value("${spring.bucket4j.config.capacity}")
    private long capacity;
    @Value("${spring.bucket4j.config.refill-greedy}")
    private long refillGreedy;
    @Value("${spring.bucket4j.config.duration}")
    private long durationGreedy;
    @Value("${spring.bucket4j.config.token-resolve}")
    private long tokenResolve;

    private String getClientIp() {
        // Hàm lấy địa chỉ IP từ request
        return RequestContextHolder.currentRequestAttributes()
                .getAttribute("ipAddress", RequestAttributes.SCOPE_REQUEST)
                .toString();
    }

    // Xử lý cho tất cả các API có @RateLimited
    @Before(value = "@annotation(com.language.service.common.RateLimited)")
    public void rateLimit(JoinPoint joinPoint) throws BusinessException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String keyMap = methodSignature.getDeclaringTypeName() + "-" + methodSignature.getMethod().getName(); // Lấy tên method API
        String clientIp = getClientIp(); // Lấy IP của client
        RateLimited rateLimitedAnnotation = methodSignature.getMethod().getAnnotation(RateLimited.class);

        long limit = rateLimitedAnnotation.limit();
        long duration = rateLimitedAnnotation.duration();

        bucketMap.putIfAbsent(keyMap, new ConcurrentHashMap<>());
        Map<String, Bucket> ipBucketMap = bucketMap.get(keyMap);

        ipBucketMap.putIfAbsent(clientIp, Bucket.builder()
                .addLimit(Bandwidth.builder()
                        .capacity(limit)
                        .refillGreedy(limit, Duration.ofSeconds(duration))
                        .build())
                .build());

        Bucket bucket = ipBucketMap.get(clientIp);

        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        if (!probe.isConsumed()) {
            throw new BusinessException(ConstantsErrorCode.ERROR_ERROR_RATE_LIMIT);
        }
    }

    // Xử lý cho tất cả các API không có @RateLimited
    @Around("execution(public * com.language.service.rest..*(..)) && !@annotation(com.language.service.common.RateLimited)")
    public Object rateLimitGlobal(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String keyMap = methodSignature.getDeclaringTypeName() + "-" + methodSignature.getMethod().getName(); // Lấy tên method API
        String clientIp = getClientIp(); // Lấy IP của client
        if (methodSignature.getMethod().getAnnotation(RateLimited.class) == null) {
            bucketMap.putIfAbsent(keyMap, new ConcurrentHashMap<>());
            Map<String, Bucket> ipBucketMap = bucketMap.get(keyMap);

            ipBucketMap.putIfAbsent(clientIp, Bucket.builder()
                    .addLimit(Bandwidth.builder()
                            .capacity(capacity)
                            .refillGreedy(refillGreedy, Duration.ofSeconds(durationGreedy))
                            .build())
                    .build());

            Bucket bucket = ipBucketMap.get(clientIp);

            ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
            if (!probe.isConsumed()) {
                throw new BusinessException(ConstantsErrorCode.ERROR_ERROR_RATE_LIMIT);
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
