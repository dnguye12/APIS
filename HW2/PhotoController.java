import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhotoController {
    private PhotoModel photoModel;
    private PhotoView photoView;

    public PhotoController(String path) {
        this.photoModel = new PhotoModel(path);
        this.photoView = new PhotoView(this.photoModel);
            this.setupListeners();
    }

    public PhotoModel getPhotoModel() {
        return this.photoModel;
    }

    public PhotoView getPhotoView() {
        return this.photoView;
    }

    private void setupListeners() {
        this.photoView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    photoModel.toggleFlip();
                    photoView.repaint();
                }
                photoView.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (photoModel.getIsFlipped()) {
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        PhotoModel.Shape currentShape = photoModel.getCurrentShape();
                        if(currentShape == PhotoModel.Shape.STROKE) {
                            Stroke currentStroke = photoModel.getCurrentStroke();
                            if (currentStroke != null) {
                                photoModel.addStroke(currentStroke);
                            }
                            photoModel.setCurrentStroke(new Stroke());
                            photoModel.addPointStroke(e.getPoint());
                            photoView.setCursor(Cursor.getDefaultCursor());
                        }else if(currentShape == PhotoModel.Shape.RECTANGLE) {
                            Rectangle currentRectangle = photoModel.getCurrentRectangle();
                            if(currentRectangle != null) {
                                photoModel.addRectangle(currentRectangle);
                            }
                            photoModel.setCurrentRectangle(new Rectangle(e.getPoint(), e.getPoint()));
                            photoView.setCursor(Cursor.getDefaultCursor());
                        }else if(currentShape == PhotoModel.Shape.ELLIPSE) {
                            Ellipse currentEllipse = photoModel.getCurrentEllipse();
                            if(currentEllipse != null) {
                                photoModel.addEllipse(currentEllipse);
                            }
                            photoModel.setCurrentEllipse(new Ellipse(e.getPoint(), e.getPoint()));
                            photoView.setCursor(Cursor.getDefaultCursor());
                        }
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        TextBlock currentTextBlock = photoModel.getCurrentTextBlock();
                        if (currentTextBlock != null) {
                            photoModel.addTextBlock(currentTextBlock);
                        }
                        photoModel.setCurrentTextBlock(new TextBlock(e.getPoint()));
                        photoView.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                        photoView.requestFocusInWindow();
                    }
                }
            }
        });
        this.photoView.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (photoModel.getIsFlipped() ) {
                    PhotoModel.Shape currentShape = photoModel.getCurrentShape();
                    if(currentShape == PhotoModel.Shape.STROKE && photoModel.getCurrentStroke() != null) {
                        photoModel.addPointStroke(e.getPoint());
                    }else if(currentShape == PhotoModel.Shape.RECTANGLE && photoModel.getCurrentRectangle() != null) {
                        Rectangle rectangle = photoModel.getCurrentRectangle();
                        rectangle.setEndPoint(e.getPoint());
                        photoModel.setCurrentRectangle(rectangle);
                    }else if(currentShape == PhotoModel.Shape.ELLIPSE && photoModel.getCurrentEllipse() != null) {
                        Ellipse ellipse = photoModel.getCurrentEllipse();
                        ellipse.setEndPoint(e.getPoint());
                        photoModel.setCurrentEllipse(ellipse);
                    }
                    photoView.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                PhotoModel.Shape currentShape = photoModel.getCurrentShape();
                if(currentShape == PhotoModel.Shape.STROKE) {
                    Stroke currentStroke = photoModel.getCurrentStroke();
                    if (photoModel.getIsFlipped() && currentStroke != null) {
                        photoModel.addStroke(currentStroke);
                        photoModel.setCurrentStroke(null);
                        photoView.repaint();
                    }
                }else if(currentShape == PhotoModel.Shape.RECTANGLE) {
                    Rectangle currentRectangle = photoModel.getCurrentRectangle();
                    if(photoModel.getIsFlipped() && currentRectangle != null) {
                        currentRectangle.setEndPoint(e.getPoint());
                        photoModel.addRectangle(currentRectangle);
                        photoModel.setCurrentRectangle(null);
                        photoView.repaint();
                    }
                }else if(currentShape == PhotoModel.Shape.ELLIPSE) {
                    Ellipse currentEllipse = photoModel.getCurrentEllipse();
                    if(photoModel.getIsFlipped() && currentEllipse != null) {
                        currentEllipse.setEndPoint(e.getPoint());
                        photoModel.addEllipse(currentEllipse);
                        photoModel.setCurrentEllipse(null);
                        photoView.repaint();
                    }
                }
            }
        });
        this.photoView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (photoModel.getIsFlipped() && photoModel.getCurrentTextBlock() != null) {
                    photoModel.addCharacterCurrentTextBlock(e.getKeyChar());
                    photoView.repaint();
                }
            }
        });
    }

    public void setPhotoModelShape(PhotoModel.Shape shape) {
        this.photoModel.setCurrentShape(shape);
    }
}
