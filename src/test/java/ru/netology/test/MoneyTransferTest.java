package ru.netology.test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    }

    @Test
    public void moneyTransferOnTheFirstCard() {

        var authInfo = getAuthInfo();
        var verificationCode = getVerificationCodeFor(authInfo);
        var cardNumber = getSecondCardBank();

        TranslationPage translationPage = new LoginPage()
                .validLogin(authInfo)
                .validVerify(verificationCode)
                .transferFirstCardBalance()
                .replenishmentCard(String.valueOf(cardNumber), "3500")
                .upDate();

        int expectedFirstCardBalance = DashboardPage.getFirstCardBalance();
        int expectedSecondCardBalance = DashboardPage.getSecondCardBalance();
        int actualFirstCardBalance = DashboardPage.getFirstCardBalance();
        int actualSecondCardBalance = DashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

    }


    @Test
    void moneyTransferOnTheSecondCard() {

        var authInfo = getAuthInfo();
        var verificationCode = getVerificationCodeFor(authInfo);
        var cardNumber = getFirstCardBank();

        new LoginPage()
                .validLogin(authInfo)
                .validVerify(verificationCode)
                .transferSecondCardBalance()
                .replenishmentCard(String.valueOf(cardNumber), "8000")
                .upDate();

        int expectedFirstCardBalance = DashboardPage.getFirstCardBalance();
        int expectedSecondCardBalance = DashboardPage.getSecondCardBalance();
        int actualFirstCardBalance = DashboardPage.getFirstCardBalance();
        int actualSecondCardBalance = DashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }

    @Test
    void moneyTransferOverLimit() {

        var authInfo = getAuthInfo();
        var verificationCode = getVerificationCodeFor(authInfo);
        var cardNumber = getSecondCardBank();

        new LoginPage()
                .validLogin(authInfo)
                .validVerify(verificationCode)
                .transferFirstCardBalance()
                .replenishmentCard(String.valueOf(cardNumber), "1500")
                .upDate();

        int expectedFirstCardBalance = DashboardPage.getFirstCardBalance();
        int expectedSecondCardBalance = DashboardPage.getSecondCardBalance();
        int actualFirstCardBalance = DashboardPage.getFirstCardBalance();
        int actualSecondCardBalance = DashboardPage.getSecondCardBalance();
        Assertions.assertEquals(expectedFirstCardBalance, actualFirstCardBalance);
        Assertions.assertEquals(expectedSecondCardBalance, actualSecondCardBalance);
    }
}
