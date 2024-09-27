package controller;

import model.*;
import model.Rectangle;
import view.PhotoView;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PhotoController {
    private PhotoModel photoModel;
    private PhotoView photoView;
    private String currentFile;

    public PhotoController(String path) {
        this.photoModel = new PhotoModel(path);
        this.photoView = new PhotoView(this.photoModel);
        this.setupListeners();
    }

    //Used for loading
    public PhotoController(PhotoModel model, String dataPath) {
        this.photoModel = model;
        this.photoView = new PhotoView(model);
        this.currentFile = dataPath;
        this.setupListeners();
    }

    public PhotoModel getPhotoModel() {
        return this.photoModel;
    }

    public PhotoView getPhotoView() {
        return this.photoView;
    }

    private void autoSave() {
        if (this.currentFile != null && !this.currentFile.isEmpty()) {
            PhotoSaveLoad.savePhotoModel(this.currentFile, this.photoModel);
        }
    }

    //Listeners for view
    private void setupListeners() {
        this.photoView.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Double-click to flip the photo
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    photoModel.toggleFlip();
                    photoView.repaint();
                }
                photoView.requestFocusInWindow();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // If the photo is flipped, handle drawing or adding text
                if (photoModel.getIsFlipped()) {
                    // Left-click actions for drawing.
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        PhotoModel.Shape currentShape = photoModel.getCurrentShape();
                        Color currentColor = photoModel.getCurrentColor();

                        //Draw an element depends on the current selected shape
                        if (currentShape == PhotoModel.Shape.STROKE) {
                            model.Stroke currentStroke = photoModel.getCurrentStroke();
                            if (currentStroke != null) {
                                photoModel.addStroke(currentStroke);
                            }
                            model.Stroke newStroke = new model.Stroke(currentColor);
                            newStroke.setStrokeSize(photoModel.getCurrentStrokeSize());
                            photoModel.setCurrentStroke(newStroke);
                            photoModel.addPointStroke(e.getPoint());
                            photoView.setCursor(Cursor.getDefaultCursor());
                        } else if (currentShape == PhotoModel.Shape.RECTANGLE) {
                            Rectangle currentRectangle = photoModel.getCurrentRectangle();
                            if (currentRectangle != null) {
                                photoModel.addRectangle(currentRectangle);
                            }
                            model.Rectangle newRectangle = new model.Rectangle(e.getPoint(), e.getPoint(), currentColor);
                            newRectangle.setStrokeSize(getPhotoModel().getCurrentStrokeSize());
                            photoModel.setCurrentRectangle(newRectangle);
                            photoView.setCursor(Cursor.getDefaultCursor());
                        } else if (currentShape == PhotoModel.Shape.ELLIPSE) {
                            Ellipse currentEllipse = photoModel.getCurrentEllipse();
                            if (currentEllipse != null) {
                                photoModel.addEllipse(currentEllipse);
                            }
                            Ellipse newEllipse = new Ellipse(e.getPoint(), e.getPoint(), currentColor);
                            newEllipse.setStrokeSize(photoModel.getCurrentStrokeSize());
                            photoModel.setCurrentEllipse(newEllipse);
                            photoView.setCursor(Cursor.getDefaultCursor());
                        }
                        // Right-click to handle adding text.
                    } else if (e.getButton() == MouseEvent.BUTTON3) {
                        TextBlock currentTextBlock = photoModel.getCurrentTextBlock();
                        if (currentTextBlock != null) {
                            photoModel.addTextBlock(currentTextBlock);
                        }
                        TextBlock newTextBlock = new TextBlock(e.getPoint(), photoModel.getCurrentColor());
                        newTextBlock.setFont(photoModel.getCurrentFont());
                        photoModel.setCurrentTextBlock(newTextBlock);
                        photoView.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); //Also change the cursor when adding text
                        photoView.requestFocusInWindow();
                    }
                }
                autoSave();
            }
        });
        // Mouse motion listener for handling dragging
        this.photoView.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (photoModel.getIsFlipped()) {
                    PhotoModel.Shape currentShape = photoModel.getCurrentShape();

                    // Update the stroke by adding points as the mouse is dragged.
                    if (currentShape == PhotoModel.Shape.STROKE && photoModel.getCurrentStroke() != null) {
                        photoModel.addPointStroke(e.getPoint());

                        // Update the rectangle/ellipse size while mouse draggin
                    } else if (currentShape == PhotoModel.Shape.RECTANGLE && photoModel.getCurrentRectangle() != null) {
                        model.Rectangle rectangle = photoModel.getCurrentRectangle();
                        rectangle.setEndPoint(e.getPoint());
                        photoModel.setCurrentRectangle(rectangle);
                    } else if (currentShape == PhotoModel.Shape.ELLIPSE && photoModel.getCurrentEllipse() != null) {
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
                // When the mouse is released, finalize the current shape (stroke, rectangle, or ellipse).
                //Then add the to the model arraylists
                if (currentShape == PhotoModel.Shape.STROKE) {
                    model.Stroke currentStroke = photoModel.getCurrentStroke();
                    if (photoModel.getIsFlipped() && currentStroke != null) {
                        currentStroke.setStrokeSize(photoModel.getCurrentStrokeSize());
                        photoModel.addStroke(currentStroke);
                        photoModel.setCurrentStroke(null);
                        photoView.repaint();
                    }
                } else if (currentShape == PhotoModel.Shape.RECTANGLE) {
                    model.Rectangle currentRectangle = photoModel.getCurrentRectangle();
                    if (photoModel.getIsFlipped() && currentRectangle != null) {
                        currentRectangle.setStrokeSize(photoModel.getCurrentStrokeSize());
                        currentRectangle.setEndPoint(e.getPoint());
                        photoModel.addRectangle(currentRectangle);
                        photoModel.setCurrentRectangle(null);
                        photoView.repaint();
                    }
                } else if (currentShape == PhotoModel.Shape.ELLIPSE) {
                    Ellipse currentEllipse = photoModel.getCurrentEllipse();
                    if (photoModel.getIsFlipped() && currentEllipse != null) {
                        currentEllipse.setStrokeSize(photoModel.getCurrentStrokeSize());
                        currentEllipse.setEndPoint(e.getPoint());
                        photoModel.addEllipse(currentEllipse);
                        photoModel.setCurrentEllipse(null);
                        photoView.repaint();
                    }
                }
                autoSave(); // <----------
            }
        });
        // Key listener for handling keyboard input on text blocks.
        this.photoView.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (photoModel.getIsFlipped() && photoModel.getCurrentTextBlock() != null) {
                    photoModel.addCharacterCurrentTextBlock(e.getKeyChar());
                    photoView.repaint();
                }
                autoSave();
            }
        });
    }

    public void setPhotoModelShape(PhotoModel.Shape shape) {
        this.photoModel.setCurrentShape(shape);
    }
}
