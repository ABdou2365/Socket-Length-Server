package ServeurChatWithJavaFX;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ServeurChat extends Application {


    @Override
    public void start(Stage primaeyStage) throws Exception {
        primaeyStage.setTitle("ServeurChat");
        BorderPane root = new BorderPane();

        Label labelHost = new Label("Host :");
        TextField textFieldHost = new TextField("localhost");

        Label labelPort = new Label("Port :");
        TextField textFieldPort = new TextField("1234");

        Button buttonConnect = new Button("Connect");

        HBox hBox = new HBox();
        VBox vBox1 = new VBox();
        hBox.setSpacing(10);
        vBox1.setSpacing(10);
        hBox.setPadding(new Insets(10));
        vBox1.setPadding(new Insets(10));
        hBox.getChildren().addAll(labelHost,textFieldHost,labelPort,textFieldPort,buttonConnect);
        root.setTop(hBox);

        ObservableList<String> observableList = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(observableList);
        vBox1.getChildren().add(listView);
        root.setCenter(vBox1);




        Scene scene = new Scene(root,700,400);
        primaeyStage.setScene(scene);
        primaeyStage.show();

        buttonConnect.setOnAction(e -> {
            String host = textFieldHost.getText();
            int port = Integer.parseInt(textFieldPort.getText());
            try {
                Socket socket = new Socket(host,port);
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                OutputStream os = socket.getOutputStream();
                PrintWriter writer = new PrintWriter(os,true);

                new Thread(()->{
                    while (true){
                        try {
                            String message = br.readLine();
                            observableList.add(message);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }).start();

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
