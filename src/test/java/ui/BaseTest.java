package ui;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class BaseTest {

    static Playwright playwright;
    static private Browser browser;
    private BrowserContext context;
    protected Page page;

    private Boolean isTraceEnabled = true;

    @BeforeAll
    public static void setUp() {
        playwright = Playwright.create();
        //BrowserType.LaunchOptions launchOptions = new BrowserType.LaunchOptions();
        //Path path = Paths.get("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        //launchOptions.setExecutablePath(path).setHeadless(false).setChannel("chrome");
        browser = playwright.chromium().launch();

    }

    @BeforeEach
    public void createContextAndPage() {
        context = browser.newContext();
        context.setDefaultTimeout(3000);
        page = context.newPage();

        //трейсинг замедляет скорость заполнение полей
        if(isTraceEnabled){
            context.tracing().start(new Tracing.StartOptions()
                    .setScreenshots(true)
                    .setSnapshots(true)
                    .setSources(false));
        }
    }

    @AfterEach
    public void closePageAndContext() {
        if(isTraceEnabled){
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("./trace/trace-" + UUID.randomUUID().toString() + ".zip")));
        }
        page.close();
        context.close();
    }

    /**
     * Закрывает браузер после выполнения всех тестов в классе
     */
    @AfterAll
    public static void tearDown() {
        browser.close();
        playwright.close();
    }

}
