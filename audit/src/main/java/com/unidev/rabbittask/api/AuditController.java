package com.unidev.rabbittask.api;

import com.unidev.rabbittask.model.Stats;
import com.unidev.rabbittask.service.AuditStatsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/audit")
public class AuditController {

    private final AuditStatsService auditStatsService;

    public AuditController(AuditStatsService auditStatsService) {
        this.auditStatsService = auditStatsService;
    }

    @GetMapping("/stats")
    public ResponseEntity<Stats> getStats() {
        return ResponseEntity.ok(auditStatsService.getAllStats());
    }

    @PostMapping("/reset")
    public ResponseEntity<Void> resetStats() {
        auditStatsService.resetStats();
        return ResponseEntity.ok().build();
    }
}
