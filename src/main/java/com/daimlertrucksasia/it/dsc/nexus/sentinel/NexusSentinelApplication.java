package com.daimlertrucksasia.it.dsc.nexus.sentinel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Nexus Sentinel application.
 * <p>
 * This class bootstraps the Spring Boot application using {@link SpringApplication#run(Class, String[])}.
 * It serves as the root configuration class and triggers component scanning, auto-configuration,
 * and other Spring Boot initialization routines.
 * </p>
 *
 * <p>
 * By default, Spring Boot performs component scanning in the package of this class and its sub-packages.
 * Optionally, a {@code scanBasePackages} attribute may be used in {@link SpringBootApplication}
 * to define custom base packages explicitly.
 * </p>
 *
 * <pre>
 * Example usage:
 *     java -jar nexus-sentinel.jar
 * </pre>
 *
 * @see SpringBootApplication
 * @see SpringApplication
 */
@SpringBootApplication
public class NexusSentinelApplication {

	/**
	 * Application entry point.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(NexusSentinelApplication.class, args);
	}
}
