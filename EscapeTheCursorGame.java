import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class EscapeTheCursorGame extends Application {
    private Circle ball;
    private Random random = new Random();
    private final int screenWidth = 800;
    private final int screenHeight = 600;
    private boolean isMoving = false;
    private int score = 0;
    private Label scoreLabel;
    private Label message;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        message = new Label("You'll never catch me! Try to click on me once LOL");
        message.setStyle("-fx-font-size: 24; -fx-text-fill: red;");
        message.setLayoutX((screenWidth - message.getWidth()) / 4 - 55);
        message.setLayoutY(screenHeight - message.getHeight() - 30);
        root.getChildren().add(message);

        ball = createBall();
        root.getChildren().add(ball);

        scoreLabel = new Label("Score: 0");
        scoreLabel.setStyle("-fx-font-size: 16; -fx-text-fill: red;");
        root.getChildren().add(scoreLabel);

        Scene scene = new Scene(root, screenWidth, screenHeight);
        scene.setOnMouseMoved(event -> moveBall(event.getSceneX(), event.getSceneY()));
        scene.setOnMouseClicked(event -> handleClick(event.getSceneX(), event.getSceneY()));

        primaryStage.setTitle("Escape the Cursor Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Circle createBall() {
        Circle circle = new Circle(20, Color.BLUE);
        circle.relocate(random.nextInt(screenWidth - (int) circle.getRadius() * 2) + circle.getRadius(),
                random.nextInt(screenHeight - (int) circle.getRadius() * 2) + circle.getRadius());
        return circle;
    }

    private void moveBall(double mouseX, double mouseY) {
        if (isMoving) {
            return;
        }

        double distanceX = Math.abs(ball.getLayoutX() + ball.getRadius() - mouseX);
        double distanceY = Math.abs(ball.getLayoutY() + ball.getRadius() - mouseY);

        if (distanceX < 50 && distanceY < 50) {
            isMoving = true;
            double newX = random.nextInt(screenWidth - (int) ball.getRadius() * 2) + ball.getRadius();
            double newY = random.nextInt(screenHeight - (int) ball.getRadius() * 2) + ball.getRadius();

            TranslateTransition transition = new TranslateTransition(Duration.millis(200), ball);
            transition.setFromX(ball.getTranslateX());
            transition.setFromY(ball.getTranslateY());
            transition.setToX(newX - ball.getLayoutX());
            transition.setToY(newY - ball.getLayoutY());
            transition.setOnFinished(event -> {
                ball.setLayoutX(newX);
                ball.setLayoutY(newY);
                ball.setTranslateX(0);
                ball.setTranslateY(0);
                isMoving = false;
            });
            transition.play();
        }
    }

    private void handleClick(double mouseX, double mouseY) {
        double distanceX = Math.abs(ball.getLayoutX() + ball.getRadius() - mouseX);
        double distanceY = Math.abs(ball.getLayoutY() + ball.getRadius() - mouseY);

        if (distanceX < 20 && distanceY < 20) {
            score++;
            scoreLabel.setText("Score: " + score);
            ball.setLayoutX(random.nextInt(screenWidth - (int) ball.getRadius() * 2) + ball.getRadius());
            ball.setLayoutY(random.nextInt(screenHeight - (int) ball.getRadius() * 2) + ball.getRadius());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
