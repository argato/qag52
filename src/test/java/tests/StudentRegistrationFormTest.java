package tests;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class StudentRegistrationFormTest {

    @BeforeAll
    static void setup() {
        Configuration.startMaximized = true;
    }

    @Test
    void successfulFillTest() {

        String
                site = "https://demoqa.com/automation-practice-form",
                firstName = "AlexName",
                lastName = "AlexFamily",
                userEmail = "aa@aa.aa",
                userNumber = "1234567890",
                dateOfBirth = "17",
                monthOfBirth = "May",
                yearOfBirth = "2000",
                gender = "Other",
                hobby = "Music",
                state = "Haryana",
                city = "Panipat",
                subject = "Chemistry",
                currentAddress = "my current address",
                fileName = "art.jpg";

        File file = new File("src/test/resources/" + fileName);
        Map<String, String> enteredData = new HashMap<>();
        enteredData.put("Student Name", firstName + " " + lastName);
        enteredData.put("Student Email", userEmail);
        enteredData.put("Gender", gender);
        enteredData.put("Mobile", userNumber);
        enteredData.put("Date of Birth", dateOfBirth + " " + monthOfBirth + "," + yearOfBirth);
        enteredData.put("Subjects", subject);
        enteredData.put("Hobbies", hobby);
        enteredData.put("Picture", fileName);
        enteredData.put("Address", currentAddress);
        enteredData.put("State and City", state + " " + city);

        open(site);
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(userEmail);
        $("#genterWrapper [for=gender-radio-3]").shouldHave(text(gender)).click();
        $("#userNumber").setValue(userNumber);
        $("#dateOfBirthInput").click();
        $(".react-datepicker__month-select").$(byText(monthOfBirth)).click();
        $(".react-datepicker__year-select").$(byText(yearOfBirth)).click();
        $(".react-datepicker__month").$(byText(dateOfBirth)).click();
        $("#subjectsInput").val(subject);
        $(".subjects-auto-complete__menu-list").$(byText(subject)).click();
        $("#hobbiesWrapper [for=hobbies-checkbox-3]").shouldHave(text(hobby))
                .click();
        $("#uploadPicture").uploadFile(file);
        $("#currentAddress").setValue(currentAddress);
        $("#stateCity-wrapper #state").click();
        $("#stateCity-wrapper #state .css-26l3qy-menu").$(byText(state)).click();
        $("#stateCity-wrapper #city").click();
        $("#stateCity-wrapper #city .css-26l3qy-menu").$(byText(city)).click();

        $("#submit").click();
        ElementsCollection rows = $$(".modal-content tbody tr");
        rows.forEach(row -> {
            ElementsCollection tds = row.$$("td");
            tds.get(1).shouldHave(exactText(enteredData.get(tds.get(0).text())));
        });
    }
}
