package com.example.demo.controller;

import com.example.demo.model.WebsiteFeatures;
import com.example.demo.service.PhishingDetectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PhishingDetectionController {

    private static final Logger logger = LoggerFactory.getLogger(PhishingDetectionController.class);
    private final PhishingDetectionService phishingService;

    @Autowired
    public PhishingDetectionController(PhishingDetectionService phishingService) {
        this.phishingService = phishingService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("websiteFeatures", new WebsiteFeatures());
        return "index"; // Should match your template name (e.g., index.html in templates/)
    }

    @PostMapping("/detect")
    public String detectPhishing(@ModelAttribute WebsiteFeatures features, Model model) {
        try {
            String result = phishingService.detectPhishing(features);
            model.addAttribute("result", result);
        } catch (Exception e) {
            logger.error("Error processing phishing detection", e);
            model.addAttribute("error", "Error processing request: " + e.getMessage());
        }
        return "result"; // Should match your result template name
    }
}