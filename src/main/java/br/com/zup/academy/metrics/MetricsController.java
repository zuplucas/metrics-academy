package br.com.zup.academy.metrics;

import io.micrometer.prometheus.PrometheusMeterRegistry;
import io.prometheus.client.Counter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/metrics-operation")
public class MetricsController {

    private PrometheusMeterRegistry registry;

    private Map<String, Counter> counterMap = new HashMap<>();

    public MetricsController(PrometheusMeterRegistry registry) {
        this.registry = registry;

        registerMetrics();
    }

    private void registerMetrics() {
        Counter counter = Counter.build().name("academy_example")
                .help("custom metric")
                .create();

        counterMap.put("academy_example", counter);
        registry.getPrometheusRegistry().register(counter);
    }

    @PostMapping("/counter/inc")
    public void incMetric() {
        counterMap.get("academy_example").inc(1);
    }
}
