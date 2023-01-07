package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TranslationPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataHelper.*;
;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void moneyTransferOnTheFirstCard() {

        var cardNumber = getSecondCardBank();
        int amount = 3500;
        int expectedFirstCardBalance = DashboardPage.getFirstCardBalance();
        int expectedSecondCardBalance = DashboardPage.getSecondCardBalance();

        var dashboardPage = new DashboardPage();

        var TranslationPage = new TranslationPage();
        dashboardPage.transferFirstCardBalance();
        TranslationPage.replenishmentCard(String.valueOf(cardNumber), String.valueOf(amount));

        int expectedNewSecondCardBalance = expectedSecondCardBalance - amount;
        int expectedNewFirstCardBalance = expectedFirstCardBalance + amount;
        int actualFirstCardBalance = DashboardPage.getFirstCardBalance();
        int actualSecondCardBalance = DashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expectedNewFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedNewSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void moneyTransferOnTheSecondCard() {


        var cardNumber = getFirstCardBank();
        int amount = 8000;
        int expectedFirstCardBalance = DashboardPage.getFirstCardBalance();
        int expectedSecondCardBalance = DashboardPage.getSecondCardBalance();
        var dashboardPage = new DashboardPage();

        var TranslationPage = new TranslationPage();
        dashboardPage.transferSecondCardBalance();
        TranslationPage.replenishmentCard(String.valueOf(cardNumber), String.valueOf(amount));


        int expectedNewSecondCardBalance = expectedSecondCardBalance + amount;
        int expectedNewFirstCardBalance = expectedFirstCardBalance - amount;
        int actualFirstCardBalance = DashboardPage.getFirstCardBalance();
        int actualSecondCardBalance = DashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expectedNewFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedNewSecondCardBalance, actualSecondCardBalance);
    }
}
