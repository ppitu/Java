import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Start {
    private static final int APP_WIDTH = 700;
    private static final int APP_HEIGHT = 850;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Start::createGUI);
    }

    private static void createGUI() {
        JFrame frame = new JFrame("Arrow mover");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(APP_WIDTH, APP_HEIGHT);
        frame.add(new MainView());
        frame.setVisible(true);
        frame.setResizable(false);
    }
}

class MainView extends JPanel {
    MainView() {
        AnimationPanel animationPanel = new AnimationPanel();
        ControlPanel controlPanel = new ControlPanel(animationPanel);

        add(animationPanel);
        add(controlPanel);
    }
}

class AnimationPanel extends JPanel {
    private List<Arrow> actualArrows = new ArrayList<>();

    AnimationPanel() {
        this.setPreferredSize(new Dimension(650, 650));
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        ((FlowLayout) this.getLayout()).setVgap(0);
        ((FlowLayout) this.getLayout()).setHgap(0);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!actualArrows.isEmpty()) {
            for (Arrow arrow : actualArrows) {
                Arrow.drawArrow(arrow.getFrom(), arrow.getTo(), arrow.getDirection(), g);
            }
        }
    }

    void drawArrow(List<Arrow> arrows) {
        this.actualArrows = arrows;
        repaint();
    }

    void clearAnimationPanel() {
        this.actualArrows = Collections.emptyList();
        repaint();
    }
}

enum ARROW_TYPE {
    UP, DOWN, LEFT, RIGHT
}

class Arrow {
    private Point from;
    private Point to;
    private ARROW_TYPE direction;

    Arrow(Point from, Point to, ARROW_TYPE direction) {
        this.from = from;
        this.to = to;
        this.direction = direction;
    }

    Point getFrom() {
        return from;
    }

    ARROW_TYPE getDirection() {
        return direction;
    }

    Point getTo() {
        return to;
    }

    static void drawArrow(Point from, Point to, ARROW_TYPE direction, Graphics graphics) {
        from = from.toAnimationPoint();
        to = to.toAnimationPoint();
        graphics.drawLine(from.getCol(), from.getRow(), to.getCol(), to.getRow());
        switch (direction) {
            case UP:
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() - 15, from.getRow() - 20);
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() + 15, from.getRow() - 20);
                break;
            case DOWN:
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() - 15, from.getRow() + 20);
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() + 15, from.getRow() + 20);
                break;
            case LEFT:
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() - 20, from.getRow() - 15);
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() - 20, from.getRow() + 15);
                break;
            case RIGHT:
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() + 20, from.getRow() - 15);
                graphics.drawLine(to.getCol(), to.getRow(), from.getCol() + 20, from.getRow() + 15);
                break;
        }
    }
}

class ControlPanel extends JPanel implements ActionListener {
    private JButton upButton;
    private JButton downButton;
    private JButton leftButton;
    private JButton rightButton;
    private JButton startButton;
    private JButton pauseButton;
    private JButton stopResetButton;
    private List<JButton> drawButtons;
    private AnimationPanel animationPanel;
    private Point actualPosition;
    private List<Arrow> allArrows = new ArrayList<>();
    private Thread animationThread;
    private boolean isAnimationThreadRunning;
    private AtomicInteger speed = new AtomicInteger(1);

    ControlPanel(AnimationPanel animationPanel) {
        super();
        this.animationPanel = animationPanel;
        this.actualPosition = new Point(6, 6);

        BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        this.setPreferredSize(new Dimension(650, 200));

        setUpButtons();
        setUpListeners();
    }

    private void setUpListeners() {
        Arrays.asList(upButton, downButton, leftButton, rightButton, startButton, pauseButton, stopResetButton)
                .forEach(button -> button.addActionListener(this));
    }

    private void setUpButtons() {
        upButton = new JButton("UP");
        downButton = new JButton("DOWN");
        leftButton = new JButton("LEFT");
        rightButton = new JButton("RIGHT");
        startButton = new JButton("START");
        pauseButton = new JButton("PAUSE");
        stopResetButton = new JButton("STOP/RESET");

        drawButtons = Arrays.asList(upButton, downButton, leftButton, rightButton);

        Box firstButtonsRow = createBoxWithButtons(drawButtons);
        Box secondButtonsRow = createBoxWithButtons(Arrays.asList(startButton, pauseButton, stopResetButton));

        add(Box.createRigidArea(new Dimension(0, 50)));
        add(firstButtonsRow);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(secondButtonsRow);
    }

    private Box createBoxWithButtons(List<JButton> buttons) {
        Box box = Box.createHorizontalBox();
        buttons.forEach(button -> {
            box.add(button);
            box.add(Box.createRigidArea(new Dimension(15, 0)));
        });
        return box;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == stopResetButton) {
            if (isAnimationThreadRunning) {
                animationThread.stop();
                startButton.setEnabled(true);
                isAnimationThreadRunning = false;
                animationPanel.drawArrow(allArrows);
            } else {
                animationPanel.clearAnimationPanel();
                allArrows.clear();
                this.actualPosition = new Point(6, 6);
            }
            drawButtons.forEach(button -> button.setEnabled(true));
            return;
        } else if (e.getSource() == startButton) {
            if (!isAnimationThreadRunning) {
                animationPanel.clearAnimationPanel();
                drawButtons.forEach(button -> button.setEnabled(false));
                startButton.setEnabled(false);
                animationThread = new Thread(() -> {
                    CopyOnWriteArrayList<Arrow> allArrowsToAnimate = new CopyOnWriteArrayList<>(allArrows);
                    CopyOnWriteArrayList<Arrow> animatedArrows = new CopyOnWriteArrayList<>();
                    int index = 0;
                    while (true) {
                        if (speed.get() != 0) {
                            Arrow arrow = allArrowsToAnimate.get(index);
                            animatedArrows.add(arrow);
                            animationPanel.drawArrow(animatedArrows);
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException ignored) {
                            }
                            index++;
                            if (index > allArrowsToAnimate.size() - 1) {
                                index = 0;
                                animatedArrows.clear();
                            }
                        }
                    }
                });
                isAnimationThreadRunning = true;
                animationThread.start();
            }
            return;
        } else if (e.getSource() == pauseButton) {
            if (speed.get() == 0) {
                speed.incrementAndGet();
            } else {
                speed.decrementAndGet();
            }
            return;
        }
        Point newPosition = null;
        ARROW_TYPE direction = null;
        if (e.getSource() == upButton) {
            if (actualPosition.getRow() - 1 >= 0) {
                newPosition = new Point(actualPosition.getRow() - 1, actualPosition.getCol());
                direction = ARROW_TYPE.UP;
            }
        } else if (e.getSource() == downButton) {
            if (actualPosition.getRow() + 1 <= 12) {
                newPosition = new Point(actualPosition.getRow() + 1, actualPosition.getCol());
                direction = ARROW_TYPE.DOWN;
            }
        } else if (e.getSource() == rightButton) {
            if (actualPosition.getCol() + 1 <= 12) {
                newPosition = new Point(actualPosition.getRow(), actualPosition.getCol() + 1);
                direction = ARROW_TYPE.RIGHT;
            }
        } else if (e.getSource() == leftButton) {
            if (actualPosition.getCol() - 1 >= 0) {
                newPosition = new Point(actualPosition.getRow(), actualPosition.getCol() - 1);
                direction = ARROW_TYPE.LEFT;
            }
        }

        Point fromCheck = null;
        if (!allArrows.isEmpty()) {
            fromCheck = allArrows.get(allArrows.size() - 1).getFrom();
        }
        if (newPosition != null && (allArrows.isEmpty() || !fromCheck.equals(newPosition))) {
            animationPanel.drawArrow(allArrows);
            allArrows.add(new Arrow(actualPosition, newPosition, direction));
            actualPosition = newPosition;
        }
    }
}

class Point {
    private int row;
    private int col;

    Point(int row, int col) {
        this.row = row;
        this.col = col;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    Point toAnimationPoint() {
        return new Point(this.getRow() * 50 + 25, this.getCol() * 50 + 25);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return row == point.row &&
                col == point.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
