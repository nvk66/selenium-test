package ui.test;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.StringUtils.isNumeric;

public class IotVega {

    @Test
    public void test() throws IOException {
        WebDriverManager.chromedriver().browserVersion("93.0.4577.82").setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("enable-automation");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-gpu");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://iotvega.com/");

        driver.findElement(By.xpath("//*[@id=\"header\"]/div[2]/div/div[2]/ul/li[2]/a")).click();

        List<Iot> iots = new ArrayList<>();
        List<WebElement> webElements3 = driver.findElements(By.cssSelector("a.main-container"));
        webElements3.forEach(webElement -> {
            String name = webElement.findElement(By.cssSelector("h2")).getText();
            List<String> features = webElement.findElement(By.cssSelector("div.product-features"))
                    .findElement(By.tagName("ul"))
                    .findElements(By.tagName("li"))
                    .stream()
                    .map(WebElement::getText)
                    .collect(Collectors.toList());
            String price = webElement.findElement(By.cssSelector("div.product-cost"))
                    .findElement(By.cssSelector(".price_item")).getText();
            price = isNumeric(price) ? deleteWhitespace(price) + " РУБ" : price;
            iots.add(new Iot(name, price, features));
        });
        System.out.println(iots);
        System.out.println(iots.size());

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Iot Vega");
        sheet.setColumnWidth(0, 6000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 10000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Название");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Цена");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Опции");
        headerCell.setCellStyle(headerStyle);

        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        int counter = 2;
        for (Iot iot : iots) {
            Row row = sheet.createRow(counter);
            Cell cell = row.createCell(0);
            cell.setCellValue(iot.getName());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(iot.getPrice());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(String.join(", ", iot.getFeatures()));
            cell.setCellStyle(style);

            counter++;
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "result.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
    }

    private static class Iot {
        private final String name;
        private final String price;
        private final List<String> features;

        public Iot(String name, String price, List<String> features) {
            this.name = name;
            this.price = price;
            this.features = features;
            System.out.println(this);
        }

        public String getName() {
            return name;
        }

        public String getPrice() {
            return price;
        }

        public List<String> getFeatures() {
            return features;
        }

        @Override
        public String toString() {
            return "Iot{" +
                    "name='" + name + '\'' +
                    ", price='" + price + '\'' +
                    ", features=" + features +
                    '}';
        }
    }
}
