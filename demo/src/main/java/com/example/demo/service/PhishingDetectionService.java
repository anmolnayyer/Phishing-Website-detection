package com.example.demo.service;

import com.example.demo.model.WebsiteFeatures;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.classifiers.functions.Logistic;
import weka.core.Instances;
import weka.core.Instance;
import weka.core.DenseInstance;
import weka.core.converters.ConverterUtils.DataSource;

import jakarta.annotation.PostConstruct;
import java.io.InputStream;

@Service
public class PhishingDetectionService {

    private Classifier classifier;
    private Instances dataStructure;

    @PostConstruct
    public void initializeModel() {
        try {
            // Load the ARFF file
            InputStream arffStream = new ClassPathResource("phishing_dataset.arff").getInputStream();
            DataSource source = new DataSource(arffStream);
            dataStructure = source.getDataSet();

            // Set the class index (assuming last attribute is the class)
            dataStructure.setClassIndex(dataStructure.numAttributes() - 1);

            // Initialize and train a classifier
            classifier = new Logistic(); // Using Logistic Regression
            classifier.buildClassifier(dataStructure);

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize Weka model", e);
        }
    }

    public String detectPhishing(WebsiteFeatures features) {
        try {
            // Create new instance
            Instance instance = new DenseInstance(dataStructure.numAttributes());
            instance.setDataset(dataStructure);

            // Set feature values
            instance.setValue(0, features.getUrl().length());       // url_length
            instance.setValue(1, features.isSslValid() ? 1 : 0);    // has_ssl
            instance.setValue(2, features.hasAtSymbol() ? 1 : 0);   // has_@

            // Classify the instance
            double result = classifier.classifyInstance(instance);

            return result == 1.0
                    ? "PHISHING WARNING: " + features.getUrl()
                    : "Legitimate website: " + features.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("Phishing detection failed", e);
        }
    }
}