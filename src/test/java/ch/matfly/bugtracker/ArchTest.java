package ch.matfly.bugtracker;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ch.matfly.bugtracker");

        noClasses()
            .that()
                .resideInAnyPackage("ch.matfly.bugtracker.service..")
            .or()
                .resideInAnyPackage("ch.matfly.bugtracker.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ch.matfly.bugtracker.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
