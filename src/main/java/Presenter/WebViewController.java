package Presenter;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class WebViewController implements Initializable {

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView.getEngine().getLoadWorker().stateProperty().addListener((observable, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                // Execute JavaScript code or interact with WebView here
            }
        });
    }

    public void loadWebContent(String pdfPath) {
        String htmlContent = getHtmlContent(pdfPath);

        System.out.println(htmlContent);

        webView.getEngine().loadContent(htmlContent);
    }

    private String getHtmlContent(String pdfPath) {
        // Create and return the HTML content with JavaScript code
        return "<html>"
                + "<head>"
                + "    <title>PDF Viewer</title>"
                + "    <script src=\"" + getClass().getResource("/pdf.js") + "\"></script>"
                + "    <script src=\"" + getClass().getResource("/pdf.worker.js") + "\"></script>"
                + "</head>"
                + "<body>"
                + "    <div id=\"pdf-container\"></div>"
                + "    <script>"
                + "        var pdfjsLib = window['pdfjs-dist/build/pdf'];"
                + "        var pdfPath = '" + pdfPath + "';"
                + "        pdfjsLib.getDocument(pdfPath).promise.then(function(pdf) {"
                + "            pdf.getPage(1).then(function(page) {"
                + "                var scale = 1.5;"
                + "                var viewport = page.getViewport({ scale: scale });"
                + "                var container = document.getElementById('pdf-container');"
                + "                container.style.width = viewport.width + 'px';"
                + "                container.style.height = viewport.height + 'px';"
                + "                page.render({"
                + "                    canvasContext: canvas.getContext('2d'),"
                + "                    viewport: viewport"
                + "                });"
                + "            });"
                + "        });"
                + "    </script>"
                + "</body>"
                + "</html>";
    }

}
