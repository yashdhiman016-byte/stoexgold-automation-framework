# Stoex Gold Automation Enterprise Framework

This project is a Selenium + TestNG automation framework for authentication and registration flows.

## Tech Stack

- Java 21
- Maven
- Selenium WebDriver
- TestNG
- Allure (test attachments/reporting)
- Apache POI (QA Excel evidence report)

## Project Structure

- `src/main/java/base`: Driver and base test setup/teardown
- `src/main/java/pages`: Page Object Model classes
- `src/main/java/utils`: Wait, test data, screenshot, and data reader helpers
- `src/main/java/listeners`: TestNG listener to collect QA evidence
- `src/main/java/reporting`: Excel report generator and custom annotation
- `src/test/java/tests`: Test classes
- `src/test/resources`: JSON test data
- `testng.xml`: Suite definition and parallel execution settings

## Prerequisites

- JDK 21 installed and available in `PATH`
- Maven installed and available in `PATH`
- Google Chrome installed

## Build

```bash
mvn clean compile
```

## Run Tests

By default, tests use `testng.xml` through Maven Surefire.

### Headed (browser visible)

```bash
mvn -Dheadless=false test
```

### Headless

```bash
mvn -Dheadless=true test
```

## Parallel Execution

Parallel settings are controlled in `testng.xml`.

Current mode:

- `parallel="classes"`
- `thread-count="2"`

Increase `thread-count` carefully based on machine/browser stability.

## Reporting

### QA Evidence Excel

Execution results and screenshot paths are written under:

- `QA_Evidence_Reports/`

### Allure Attachments

Allure result files are written under:

- `allure-results/`

To generate a local Allure HTML report (if Allure CLI is installed):

```bash
allure serve allure-results
```

## Troubleshooting

- Browser startup failures:
  - Reduce parallel `thread-count` in `testng.xml`
  - Ensure no stale Chrome/ChromeDriver processes are running
- Driver download/network issues:
  - Ensure internet/proxy settings allow driver metadata access
  - Keep a compatible ChromeDriver available in cache/PATH
- Permission issues:
  - Ensure user has write access to project folders and Selenium cache directories
