/*
 *  Copyright (C) 2010-2024 JPEXS
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.jpexs.decompiler.flash.easygui;

import com.jpexs.decompiler.flash.ReadOnlyTagList;
import com.jpexs.decompiler.flash.tags.RemoveObject2Tag;
import com.jpexs.decompiler.flash.tags.ShowFrameTag;
import com.jpexs.decompiler.flash.tags.Tag;
import com.jpexs.decompiler.flash.tags.base.CharacterTag;
import com.jpexs.decompiler.flash.tags.base.MorphShapeTag;
import com.jpexs.decompiler.flash.tags.base.PlaceObjectTypeTag;
import com.jpexs.decompiler.flash.tags.base.RemoveTag;
import com.jpexs.decompiler.flash.timeline.DepthState;
import com.jpexs.decompiler.flash.timeline.Timeline;
import com.jpexs.decompiler.flash.timeline.Timelined;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.pushingpixels.substance.internal.utils.SubstanceColorUtilities;

/**
 * @author JPEXS
 */
public class TimelineBodyPanel extends JPanel implements MouseListener, KeyListener {

    private Timeline timeline;

    public static final int FRAME_WIDTH = 8;

    public static final int FRAME_HEIGHT = 18;

    public static final Color SHAPE_TWEEN_COLOR = new Color(0x59, 0xfe, 0x7c);

    public static final Color MOTION_TWEEN_COLOR = new Color(0xd1, 0xac, 0xf1);

    //public static final Color frameColor = new Color(0xbd, 0xd8, 0xfc);
    public static final Color BORDER_COLOR = Color.black;

    public static final Color EMPTY_BORDER_COLOR = new Color(0xbd, 0xd8, 0xfc);

    public static final Color KEY_COLOR = Color.black;

    public static final Color A_COLOR = Color.black;

    public static final Color STOP_COLOR = Color.white;

    public static final Color STOP_BORDER_COLOR = Color.black;

    public static final Color BORDER_LINES_COLOR = new Color(0xde, 0xde, 0xde);

    public static final Color SELECTED_COLOR = new Color(0xff, 0x99, 0x99);

    public static final Color SELECTED_BORDER_COLOR = new Color(0xcc, 0, 0);

    //public static final Color SELECTED_COLOR = new Color(113, 174, 235);
    public static final int BORDER_LINES_LENGTH = 2;

    public static final float FONT_SIZE = 10.0f;

    private final List<FrameSelectionListener> selectionListeners = new ArrayList<>();

    private final List<Runnable> changeListeners = new ArrayList<>();

    public Rectangle cursor = null;

    private int frame = 0;

    private int endFrame = 0;

    private int depth = 0;

    private int endDepth = 0;

    private final UndoManager undoManager;

    private boolean ctrlDown = false;

    private boolean altDown = false;

    private boolean shiftDown = false;

    private enum BlockType {

        EMPTY, NORMAL, MOTION_TWEEN, SHAPE_TWEEN
    }

    public static Color getEmptyFrameColor() {
        return SubstanceColorUtilities.getLighterColor(getControlColor(), 0.7);
    }

    public static Color getEmptyFrameSecondColor() {
        return SubstanceColorUtilities.getLighterColor(getControlColor(), 0.9);
    }

    public static Color getBackgroundColor() {
        return SystemColor.control;
    }

    public static Color getSelectedColor() {
        return SystemColor.textHighlight;
    }

    public static Color getSelectedColorText() {
        return SystemColor.textHighlightText;
    }

    private static Color getControlColor() {
        return SystemColor.control;
    }

    public static Color getFrameColor() {
        return SubstanceColorUtilities.getDarkerColor(getControlColor(), 0.1);
    }

    public void addFrameSelectionListener(FrameSelectionListener l) {
        selectionListeners.add(l);
    }

    public void removeFrameSelectionListener(FrameSelectionListener l) {
        selectionListeners.remove(l);
    }

    public void addChangeListener(Runnable l) {
        changeListeners.add(l);
    }

    public void removeChangeListener(Runnable l) {
        changeListeners.remove(l);
    }

    private void fireChanged() {
        for (Runnable l : changeListeners) {
            l.run();
        }
    }

    public TimelineBodyPanel(UndoManager undoManager) {
        refresh();
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        this.undoManager = undoManager;

        KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if ((e.getID() == KeyEvent.KEY_PRESSED) || (e.getID() == KeyEvent.KEY_RELEASED)) {
                    ctrlDown = ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK);
                    altDown = ((e.getModifiersEx() & InputEvent.ALT_DOWN_MASK) == InputEvent.ALT_DOWN_MASK);
                    shiftDown = ((e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) == InputEvent.SHIFT_DOWN_MASK);
                }
                return false;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g1) {

        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackgroundColor());
        g.fillRect(0, 0, getWidth(), getHeight());
        if (timeline == null) {
            return;
        }
        Rectangle clip = g.getClipBounds();
        int frameWidth = FRAME_WIDTH;
        int frameHeight = FRAME_HEIGHT;
        int start_f = clip.x / frameWidth;
        int start_d = clip.y / frameHeight;
        int end_f = (clip.x + clip.width) / frameWidth;
        int end_d = (clip.y + clip.height) / frameHeight;

        int max_d = timeline.getMaxDepth();
        if (max_d < end_d) {
            end_d = max_d;
        }
        int max_f = timeline.getFrameCount() - 1;
        if (max_f < end_f) {
            end_f = max_f;
        }

        if (end_d - start_d + 1 < 0) {
            return;
        }

        // draw background
        for (int f = start_f; f <= end_f; f++) {
            g.setColor((f + 1) % 5 == 0 ? getEmptyFrameSecondColor() : getEmptyFrameColor());
            g.fillRect(f * frameWidth, start_d * frameHeight, frameWidth, (end_d - start_d + 1) * frameHeight);
            g.setColor(EMPTY_BORDER_COLOR);
            for (int d = start_d; d <= end_d; d++) {
                g.drawRect(f * frameWidth, d * frameHeight, frameWidth, frameHeight);
            }
        }

        // draw selected cell
        if (cursor != null) {
            g.setColor(getSelectedColor());
            g.fillRect(cursor.x * frameWidth + 1, cursor.y * frameHeight + 1, (frameWidth * cursor.width) - 1, (frameHeight * cursor.height) - 1);
        }

        int awidth = g.getFontMetrics().stringWidth("a");
        boolean firstAction = true;
        for (int f = start_f; f <= end_f || (firstAction && f <= max_f); f++) {
            if (!timeline.getFrame(f).actions.isEmpty()) {
                if (firstAction) {
                    drawBlock(g, getEmptyFrameColor(), 0, 0, f, BlockType.EMPTY);
                }

                int f2 = f + 1;
                while (f2 <= max_f && timeline.getFrame(f2).actions.isEmpty()) {
                    f2++;
                }
                drawBlock(g, getEmptyFrameColor(), 0, f, f2 - f, BlockType.EMPTY);
                g.setColor(A_COLOR);
                g.setFont(getFont().deriveFont(FONT_SIZE));
                g.drawString("a", f * frameWidth + frameWidth / 2 - awidth / 2, frameHeight / 2);
                firstAction = false;
            }
        }

        Map<Integer, Integer> depthMaxFrames = timeline.getDepthMaxFrame();
        for (int d = start_d; d <= end_d; d++) {
            int maxFrame = depthMaxFrames.containsKey(d) ? depthMaxFrames.get(d) : -1;
            if (maxFrame < 0) {
                continue;
            }

            int end_f2 = Math.min(end_f, maxFrame);
            int start_f2 = Math.min(start_f, end_f2);

            // find the start frame number of the current block
            DepthState dsStart = timeline.getFrame(start_f2).layers.get(d);
            for (; start_f2 >= 1; start_f2--) {
                DepthState ds = timeline.getFrame(start_f2 - 1).layers.get(d);
                if (((dsStart == null) != (ds == null))
                        || (ds != null && (dsStart.characterId != ds.characterId || !Objects.equals(dsStart.className, ds.className)))) {
                    break;
                }
            }

            for (int f = start_f2; f <= end_f2; f++) {
                DepthState fl = timeline.getFrame(f).layers.get(d);
                boolean motionTween = fl == null ? false : fl.motionTween;

                DepthState flNext = f < max_f ? timeline.getFrame(f + 1).layers.get(d) : null;
                DepthState flPrev = f > 0 ? timeline.getFrame(f - 1).layers.get(d) : null;

                CharacterTag cht = fl == null ? null : fl.getCharacter();
                boolean shapeTween = cht != null && (cht instanceof MorphShapeTag);
                boolean motionTweenStart = !motionTween && (flNext != null && flNext.motionTween);
                boolean motionTweenEnd = !motionTween && (flPrev != null && flPrev.motionTween);
                //boolean shapeTweenStart = shapeTween && (flPrev == null || flPrev.characterId != fl.characterId);
                //boolean shapeTweenEnd = shapeTween && (flNext == null || flNext.characterId != fl.characterId);

                /*if (motionTweenStart || motionTweenEnd) {
                 motionTween = true;
                 }*/
                int draw_f = f;
                int num_frames = 1;
                Color backColor;
                BlockType blockType;
                if (fl == null) {
                    for (; f + 1 < timeline.getFrameCount(); f++) {
                        fl = timeline.getFrame(f + 1).layers.get(d);
                        if (fl != null && fl.getCharacter() != null) {
                            break;
                        }

                        num_frames++;
                    }

                    backColor = getEmptyFrameColor();
                    blockType = BlockType.EMPTY;
                } else {
                    for (; f + 1 < timeline.getFrameCount(); f++) {
                        fl = timeline.getFrame(f + 1).layers.get(d);
                        if (fl == null || fl.key) {
                            break;
                        }

                        num_frames++;
                    }

                    backColor = shapeTween ? SHAPE_TWEEN_COLOR : motionTween ? MOTION_TWEEN_COLOR : getFrameColor();
                    blockType = shapeTween ? BlockType.SHAPE_TWEEN : motionTween ? BlockType.MOTION_TWEEN : BlockType.NORMAL;
                }

                drawBlock(g, backColor, d, draw_f, num_frames, blockType);
            }
        }

        if (cursor != null && cursor.x >= start_f && cursor.x <= end_f) {
            g.setColor(SELECTED_BORDER_COLOR);
            g.drawLine(cursor.x * frameWidth + frameWidth / 2, 0, cursor.x * frameWidth + frameWidth / 2, getHeight());
        }
    }

    private void drawBlock(Graphics2D g, Color backColor, int depth, int frame, int num_frames, BlockType blockType) {
        int frameWidth = FRAME_WIDTH;
        int frameHeight = FRAME_HEIGHT;

        for (int n = frame; n < frame + num_frames; n++) {
            if (cursor != null && cursor.contains(n, depth)) {
                g.setColor(getSelectedColor());
            } else {
                g.setColor(backColor);
            }
            g.fillRect(n * frameWidth, depth * frameHeight, frameWidth, frameHeight);
        }

        g.setColor(BORDER_COLOR);
        g.drawRect(frame * frameWidth, depth * frameHeight, num_frames * frameWidth, frameHeight);

        boolean selected = cursor != null && cursor.contains(cursor.x, depth);
        /*if (cursor != null && cursor.contains(frame, depth) && !cursor.contains(frame + num_frames, depth)) {//frame <= cursor.x && (frame + num_frames) > cursor.x && depth == cursor.y) {
            selected = true;
        }*/

        if (cursor != null) {
            for (int n = frame + 1; n < frame + num_frames; n++) {
                g.setColor(getSelectedColor());
                if (cursor.contains(n, depth)) {
                    g.fillRect(n * frameWidth + 1, depth * frameHeight + 1, frameWidth, frameHeight);
                }
            }
        }
        /*if (selected) {
            g.setColor(getSelectedColor());
            g.fillRect(cursor.x * frameWidth + 1, depth * frameHeight + 1, (cursor.width * frameWidth) - 1, frameHeight - 1);
        }*/

        boolean isTween = blockType == BlockType.MOTION_TWEEN || blockType == BlockType.SHAPE_TWEEN;

        g.setColor(KEY_COLOR);
        if (isTween) {
            g.drawLine(frame * frameWidth, depth * frameHeight + frameHeight * 3 / 4,
                    frame * frameWidth + num_frames * frameWidth - frameWidth / 2, depth * frameHeight + frameHeight * 3 / 4
            );
        }

        if (cursor != null && cursor.contains(frame, depth)) {
            g.setBackground(getSelectedColorText());
        } else {
            g.setColor(KEY_COLOR);
        }
        if (blockType == BlockType.EMPTY) {
            g.drawOval(frame * frameWidth + frameWidth / 4, depth * frameHeight + frameHeight * 3 / 4 - frameWidth / 2 / 2, frameWidth / 2, frameWidth / 2);
        } else {
            g.fillOval(frame * frameWidth + frameWidth / 4, depth * frameHeight + frameHeight * 3 / 4 - frameWidth / 2 / 2, frameWidth / 2, frameWidth / 2);
        }

        if (num_frames > 1) {
            int endFrame = frame + num_frames - 1;
            if (cursor != null && cursor.contains(endFrame, depth)) {
                g.setBackground(getSelectedColorText());
            } else {
                g.setColor(KEY_COLOR);
            }
            if (isTween) {
                g.fillOval(endFrame * frameWidth + frameWidth / 4, depth * frameHeight + frameHeight * 3 / 4 - frameWidth / 2 / 2, frameWidth / 2, frameWidth / 2);
            } else {

                if (cursor != null && cursor.contains(endFrame, depth)) {
                    g.setBackground(getSelectedColorText());
                } else {
                    g.setColor(STOP_COLOR);
                }
                g.fillRect(endFrame * frameWidth + frameWidth / 4, depth * frameHeight + frameHeight / 2 - 2, frameWidth / 2, frameHeight / 2);
                if (cursor != null && cursor.contains(endFrame, depth)) {
                    g.setBackground(getSelectedColorText());
                } else {
                    g.setColor(STOP_BORDER_COLOR);
                }
                g.drawRect(endFrame * frameWidth + frameWidth / 4, depth * frameHeight + frameHeight / 2 - 2, frameWidth / 2, frameHeight / 2);
            }

            for (int n = frame + 1; n < frame + num_frames; n++) {
                if (cursor != null && cursor.contains(n, depth)) {
                    g.setBackground(getSelectedColorText());
                } else {
                    g.setColor(BORDER_LINES_COLOR);
                }
                g.drawLine(n * frameWidth, depth * frameHeight + 1, n * frameWidth, depth * frameHeight + BORDER_LINES_LENGTH);
                g.drawLine(n * frameWidth, depth * frameHeight + frameHeight - 1, n * frameWidth, depth * frameHeight + frameHeight - BORDER_LINES_LENGTH);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    public void depthSelect(int depth) {
        frameSelect(frame, depth);
    }

    public void rectSelect(int frame, int depth, int endFrame, int endDepth) {
        int x1 = Math.min(frame, endFrame);
        int x2 = Math.max(frame, endFrame);
        int y1 = Math.min(depth, endDepth);
        int y2 = Math.max(depth, endDepth);
        cursor = new Rectangle(
                x1,
                y1,
                x2 - x1 + 1,
                y2 - y1 + 1);
        repaint();
        this.frame = frame;
        this.depth = depth;
        this.endFrame = endFrame;
        this.endDepth = endDepth;
        scrollRectToVisible(getFrameBounds(frame, depth));
        fireFrameSelected(frame, depth);
    }

    public void frameSelect(int frame, int depth) {
        if (cursor != null && cursor.width == 1 && cursor.height == 1 && (cursor.contains(frame, depth) || (depth == -1 && cursor.contains(frame, cursor.y)))) {
            return;
        }
        if (depth == -1 && cursor != null) {
            depth = cursor.y;
        }
        rectSelect(frame, depth, frame, depth);
    }

    private void fireFrameSelected(int frame, int depth) {
        for (FrameSelectionListener l : selectionListeners) {
            l.frameSelected(frame, depth);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        p.x = p.x / FRAME_WIDTH;
        p.y = p.y / FRAME_HEIGHT;
        if (p.x >= timeline.getFrameCount()) {
            p.x = timeline.getFrameCount() - 1;
        }
        int maxDepth = timeline.getMaxDepth();
        if (p.y > maxDepth) {
            p.y = maxDepth;
        }
        if (shiftDown) {
            /*int x1 = frame;
            int x2 = p.x;
            if (x2 < x1) {
                x1 = p.x;
                x2 = frame;
            }
            int y1 = depth;
            int y2 = p.y;
            if (y2 < y1) {
                y1 = p.y;
                y2 = depth;
            }*/
            rectSelect(frame, depth, p.x, p.y);
        } else {
            if (!(cursor != null && cursor.contains(p) && SwingUtilities.isRightMouseButton(e))) {
                frameSelect(p.x, p.y);
            }
        }
        requestFocusInWindow();
        if (SwingUtilities.isRightMouseButton(e)) {

            JPopupMenu popupMenu = new JPopupMenu();

            DepthState ds = timeline.getFrame(frame).layers.get(depth);
            boolean thisEmpty = ds == null || ds.getCharacter() == null;
            boolean previousEmpty = true;
            boolean emptyDepth = true;
            boolean somethingBefore = false;
            boolean somethingAfter = false;
            if (frame > 0) {
                ds = timeline.getFrame(frame - 1).layers.get(depth);
                previousEmpty = ds == null || ds.getCharacter() == null;
            }
            for (int f = frame - 1; f >= 0; f--) {
                ds = timeline.getFrame(f).layers.get(depth);
                boolean empty = ds == null || ds.getCharacter() == null;
                if (!empty) {
                    somethingBefore = true;
                    break;
                }
            }
            for (int f = frame + 1; f < timeline.getFrameCount(); f++) {
                ds = timeline.getFrame(f).layers.get(depth);
                boolean empty = ds == null || ds.getCharacter() == null;
                if (!empty) {
                    somethingAfter = true;
                    break;
                }
            }
            emptyDepth = thisEmpty && !somethingBefore && !somethingAfter;

            JMenuItem addKeyFrameMenuItem = new JMenuItem(EasyStrings.translate("action.addKeyFrame"));
            addKeyFrameMenuItem.addActionListener(this::addKeyFrame);
            JMenuItem addKeyFrameEmptyBeforeMenuItem = new JMenuItem(EasyStrings.translate("action.addKeyFrameWithBlankFrameBefore"));
            addKeyFrameEmptyBeforeMenuItem.addActionListener(this::addKeyFrameEmptyBefore);
            JMenuItem addFrameMenuItem = new JMenuItem(EasyStrings.translate("action.addFrame"));
            addFrameMenuItem.addActionListener(this::addFrame);
            JMenuItem removeFrameMenuItem = new JMenuItem(EasyStrings.translate("action.removeFrame"));
            removeFrameMenuItem.addActionListener(this::removeFrame);

            boolean multiSelect = cursor != null && (cursor.width > 1 || cursor.height > 1);

            if (!thisEmpty || multiSelect) {
                popupMenu.add(addKeyFrameMenuItem);
            }

            if (thisEmpty && previousEmpty && somethingBefore && !somethingAfter && !multiSelect) {
                popupMenu.add(addKeyFrameEmptyBeforeMenuItem);
            }
            if (!emptyDepth || multiSelect) {
                popupMenu.add(addFrameMenuItem);
            }

            if (!thisEmpty || somethingAfter || multiSelect) {
                popupMenu.add(removeFrameMenuItem);
            }

            if (popupMenu.getComponentCount() > 0) {
                popupMenu.show(this, e.getX(), e.getY());
            }
        }
    }

    private void removeFrame(ActionEvent e) {
        final int fframe = Math.min(frame, endFrame);
        final int fdepth = Math.min(depth, endDepth);
        final int fendFrame = Math.max(frame, endFrame);
        final int fendDepth = Math.max(depth, endDepth);
        undoManager.doOperation(new TimelinedTagListDoableOperation(timeline.timelined) {

            @Override
            public void doOperation() {
                super.doOperation();
                Timelined timelined = timeline.timelined;
                ReadOnlyTagList tags = timelined.getTags();

                for (int nf = 0; nf < fendFrame - fframe + 1; nf++) {
                    for (int nd = fdepth; nd <= fendDepth; nd++) {

                        int f = timelined.getFrameCount();
                        List<Tag> lastFrameDepthTags = new ArrayList<>();
                        DepthState ds = timeline.getFrame(fframe).layers.get(nd);
                        if (ds != null && ds.key) {
                            PlaceObjectTypeTag po = ds.placeObjectTag;
                            ShowFrameTag sf = timeline.getFrame(fframe).showFrameTag;
                            int pos = sf == null ? tags.size() : timelined.indexOfTag(sf);
                            for (int i = pos + 1; i < tags.size(); i++) {
                                Tag t = tags.get(i);
                                if (t instanceof RemoveObject2Tag) {
                                    RemoveObject2Tag rt = (RemoveObject2Tag) t;
                                    if (rt.depth == nd) {
                                        timelined.removeTag(po);
                                        timelined.removeTag(rt);
                                        i--;
                                        i--;
                                    }
                                }
                                if (t instanceof ShowFrameTag) {
                                    break;
                                }
                            }
                        }

                        boolean endsWithRemove = false;
                        for (int i = tags.size() - 1; i >= 0; i--) {
                            Tag t = tags.get(i);
                            if (t instanceof PlaceObjectTypeTag) {
                                PlaceObjectTypeTag pt = (PlaceObjectTypeTag) t;
                                if (pt.getDepth() == nd) {
                                    break;
                                }
                            }
                            if (t instanceof RemoveTag) {
                                RemoveTag rt = (RemoveTag) t;
                                if (rt.getDepth() == nd) {
                                    endsWithRemove = true;
                                    break;
                                }
                            }
                        }

                        for (int i = tags.size() - 1; i >= 0; i--) {
                            Tag t = tags.get(i);
                            if (t instanceof PlaceObjectTypeTag) {
                                PlaceObjectTypeTag pt = (PlaceObjectTypeTag) t;
                                if (pt.getDepth() == nd) {
                                    lastFrameDepthTags.add(pt);
                                    timelined.removeTag(i);
                                }
                            }
                            if (t instanceof RemoveTag) {
                                RemoveTag rt = (RemoveTag) t;
                                if (rt.getDepth() == nd) {
                                    lastFrameDepthTags.add(rt);
                                    timelined.removeTag(i);
                                }
                            }
                            if (t instanceof ShowFrameTag) {
                                for (Tag lt : lastFrameDepthTags) {
                                    timelined.addTag(i, lt);
                                }
                                lastFrameDepthTags.clear();
                                f--;
                                if (f == fframe) {
                                    break;
                                }
                            }
                        }

                        if (!endsWithRemove) {
                            RemoveTag rt = new RemoveObject2Tag(timelined.getSwf());
                            rt.setTimelined(timelined);
                            rt.setDepth(nd);
                            Tag lt = tags.get(tags.size() - 1);
                            if (lt instanceof ShowFrameTag) {
                                timelined.addTag(tags.size() - 1, rt);
                            } else {
                                timelined.addTag(lt);
                            }
                        }
                    }
                }

                timelined.resetTimeline();

                /*System.err.println("=====AFTER=======");
                f = 0;
                int i = 0;
                for (Tag t : timelined.getTags()) {
                    if (t instanceof ShowFrameTag) {
                        System.err.println("" + i + ": frame " + f);
                        f++;
                    } else {
                        System.err.println("" + i + ": " + t);                    
                    }
                    i++;
                }*/
                frameSelect(fframe, fdepth);
                timeline = timelined.getTimeline();

                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public void undoOperation() {
                super.undoOperation();
                timeline = timeline.timelined.getTimeline();

                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public String getDescription() {
                return EasyStrings.translate("action.removeFrame");
            }
        });
    }

    private void addKeyFrame(ActionEvent e) {
        if (frame == endFrame && depth == endDepth && timeline.getFrame(frame).layers.get(depth).key) {
            return;
        }
        final int fframe = Math.min(frame, endFrame);
        final int fdepth = Math.min(depth, endDepth);
        final int fendFrame = Math.max(frame, endFrame);
        final int fendDepth = Math.max(depth, endDepth);
        
        undoManager.doOperation(new TimelinedTagListDoableOperation(timeline.timelined) {

            @Override
            public void doOperation() {
                super.doOperation();
                Timelined timelined = timeline.timelined;
                
                for (int nf = frame; nf <= fendFrame; nf++) {
                    for (int nd = fdepth; nd <= fendDepth; nd++) {
                        DepthState ds = timeline.getFrame(nf).layers.get(nd);
                        if (ds.key) {
                            continue;
                        }
                        ds.key = true;
                        /*RemoveTag rm = new RemoveObject2Tag(timelined.getSwf());
                        rm.setDepth(depth);
                        rm.setTimelined(timelined);*/
                        PlaceObjectTypeTag place;
                        try {
                            place = (PlaceObjectTypeTag) ds.placeObjectTag.cloneTag();
                        } catch (InterruptedException | IOException ex) {
                            //should not happen
                            return;
                        }
                        place.setTimelined(timelined);
                        place.setPlaceFlagMove(true);
                        ShowFrameTag sf = timeline.getFrame(nf).showFrameTag;
                        int pos;
                        if (sf != null) {
                            pos = timelined.indexOfTag(sf);
                        } else {
                            pos = timelined.getTags().size();
                        }
                        //timelined.addTag(pos++, rm);

                        timelined.addTag(pos++, place);
                    }
                }

                timelined.resetTimeline();
                timeline = timelined.getTimeline();

                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public void undoOperation() {
                super.undoOperation();
                timeline = timeline.timelined.getTimeline();
                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public String getDescription() {
                return EasyStrings.translate("action.addKeyFrame");
            }
        });
    }

    private void addKeyFrameEmptyBefore(ActionEvent e) {
        undoManager.doOperation(new TimelinedTagListDoableOperation(timeline.timelined) {

            @Override
            public void doOperation() {
                super.doOperation();
                Timelined timelined = timeline.timelined;
                DepthState ds = null;
                for (int f = frame - 1; f >= 0; f--) {
                    ds = timeline.getFrame(f).layers.get(depth);
                    if (ds != null && ds.getCharacter() != null) {
                        break;
                    }
                }

                PlaceObjectTypeTag place;
                try {
                    place = (PlaceObjectTypeTag) ds.placeObjectTag.cloneTag();
                } catch (InterruptedException | IOException ex) {
                    //should not happen
                    return;
                }
                place.setTimelined(timelined);
                place.setPlaceFlagMove(false);
                ShowFrameTag sf = timeline.getFrame(frame).showFrameTag;
                int pos;
                if (sf != null) {
                    pos = timelined.indexOfTag(sf);

                    if (frame < timelined.getFrameCount() - 1) {
                        RemoveTag rm = new RemoveObject2Tag(timelined.getSwf());
                        rm.setTimelined(timelined);
                        rm.setDepth(depth);
                        timelined.addTag(pos + 1, rm);
                    }
                } else {
                    pos = timelined.getTags().size();
                }
                timelined.addTag(pos++, place);

                timelined.resetTimeline();
                timeline = timelined.getTimeline();

                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public void undoOperation() {
                super.undoOperation();
                timeline = timeline.timelined.getTimeline();
                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public String getDescription() {
                return EasyStrings.translate("action.addKeyFrame"); //Intentionally not "...with blank frames before"
            }
        });
    }

    private void addFrame(ActionEvent e) {
        final int fframe = Math.min(frame, endFrame);
        final int fdepth = Math.min(depth, endDepth);
        final int fendFrame = Math.max(frame, endFrame);
        final int fendDepth = Math.max(depth, endDepth);

        undoManager.doOperation(new TimelinedTagListDoableOperation(timeline.timelined) {
            @Override
            public void doOperation() {
                super.doOperation();
                DepthState ds;
                Timelined timelined = timeline.timelined;

                for (int nf = 0; nf < fendFrame - fframe + 1; nf++) {
                    for (int nd = fdepth; nd <= fendDepth; nd++) {

                        boolean somethingAfter = false;
                        for (int f = fframe + 1; f < timeline.getFrameCount(); f++) {
                            ds = timeline.getFrame(f).layers.get(nd);
                            boolean empty = ds == null || ds.getCharacter() == null;
                            if (!empty) {
                                somethingAfter = true;
                                break;
                            }
                        }

                        for (int f = fframe; f >= 0; f--) {
                            ds = timeline.getFrame(f).layers.get(nd);
                            boolean empty = ds == null || ds.getCharacter() == null;
                            if (!empty || somethingAfter) {
                                int moveFrameCount = fframe - f;
                                if (moveFrameCount == 0) {
                                    moveFrameCount = 1;
                                }
                                boolean frameAdded = false;
                                for (int mf = 0; mf < moveFrameCount; mf++) {
                                    int pos = timelined.indexOfTag(timeline.getFrame(f).showFrameTag);
                                    ReadOnlyTagList tags = timelined.getTags();
                                    List<Tag> lastFrameDepthTags = new ArrayList<>();
                                    int f2 = f + 1;
                                    boolean endsWithRemove = false;
                                    for (int i = pos + 1; i < tags.size(); i++) {
                                        Tag t = tags.get(i);
                                        if (t instanceof PlaceObjectTypeTag) {
                                            PlaceObjectTypeTag po = (PlaceObjectTypeTag) t;
                                            if (po.getDepth() == nd) {
                                                lastFrameDepthTags.add(po);
                                                timelined.removeTag(po);
                                                endsWithRemove = false;
                                                i--;
                                            }
                                        }
                                        if (t instanceof RemoveTag) {
                                            RemoveTag ro = (RemoveTag) t;
                                            if (ro.getDepth() == nd) {
                                                lastFrameDepthTags.add(ro);
                                                timelined.removeTag(ro);
                                                endsWithRemove = true;
                                                i--;
                                            }
                                        }
                                        if ((t instanceof ShowFrameTag) || (i == tags.size() - 1)) {
                                            if (!(t instanceof ShowFrameTag) && !lastFrameDepthTags.isEmpty()) {
                                                ShowFrameTag sf = new ShowFrameTag(timelined.getSwf());
                                                sf.setTimelined(timelined);
                                                timelined.addTag(sf);
                                                i++;
                                            }

                                            boolean removeOnly = true;
                                            for (Tag lt : lastFrameDepthTags) {
                                                if (!(lt instanceof RemoveTag)) {
                                                    removeOnly = false;
                                                    break;
                                                }
                                            }

                                            boolean onLastFrame = f2 == timelined.getFrameCount() - 1;

                                            if (!(removeOnly && onLastFrame)) {
                                                for (Tag lt : lastFrameDepthTags) {
                                                    i++;
                                                    timelined.addTag(i, lt);
                                                }
                                            }

                                            //Add beyond current total frameCount
                                            if (onLastFrame) {
                                                if ((!removeOnly && !lastFrameDepthTags.isEmpty()) || !endsWithRemove) {
                                                    //Add removeTag to other layers
                                                    for (int d = 1; d <= timeline.maxDepth; d++) {
                                                        if (d == nd) {
                                                            continue;
                                                        }
                                                        ds = timeline.getFrame(f2).layers.get(d);
                                                        if (ds != null && ds.getCharacter() != null) {
                                                            RemoveTag rt = new RemoveObject2Tag(timelined.getSwf());
                                                            rt.setTimelined(timelined);
                                                            rt.setDepth(d);
                                                            timelined.addTag(i, rt);
                                                            i++;
                                                        }
                                                    }

                                                    frameAdded = true;
                                                    ShowFrameTag sf = new ShowFrameTag(timelined.getSwf());
                                                    sf.setTimelined(timelined);
                                                    timelined.addTag(sf);
                                                    i++;
                                                    timelined.setFrameCount(timelined.getFrameCount() + 1);
                                                }
                                            }
                                            lastFrameDepthTags.clear();
                                            f2++;
                                        }
                                    }
                                }
                                if (!frameAdded && fframe == timelined.getFrameCount() - 1) {
                                    //Add removeTag to other layers
                                    for (int d = 1; d <= timeline.maxDepth; d++) {
                                        if (d == nd) {
                                            continue;
                                        }
                                        ds = timeline.getFrame(fframe).layers.get(d);
                                        if (ds != null && ds.getCharacter() != null) {
                                            RemoveTag rt = new RemoveObject2Tag(timelined.getSwf());
                                            rt.setTimelined(timelined);
                                            rt.setDepth(d);
                                            timelined.addTag(rt);
                                        }
                                    }
                                    for (int mf = 0; mf < moveFrameCount; mf++) {
                                        ShowFrameTag sf = new ShowFrameTag(timelined.getSwf());
                                        sf.setTimelined(timelined);
                                        timelined.addTag(sf);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }

                timelined.resetTimeline();
                timelined.setFrameCount(timelined.getTimeline().getFrameCount());
                timeline = timelined.getTimeline();
                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public void undoOperation() {
                super.undoOperation();
                timeline = timeline.timelined.getTimeline();
                refresh();
                fireChanged();
                repaint();
            }

            @Override
            public String getDescription() {
                return EasyStrings.translate("action.addFrame");
            }
        });
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case 37: //left
                if (cursor.x > 0) {
                    frameSelect(cursor.x - 1, cursor.y);
                }
                break;
            case 39: //right
                if (cursor.x < timeline.getFrameCount() - 1) {
                    frameSelect(cursor.x + 1, cursor.y);
                }
                break;
            case 38: //up
                if (cursor.y > 0) {
                    frameSelect(cursor.x, cursor.y - 1);
                }
                break;
            case 40: //down
                if (cursor.y < timeline.getMaxDepth()) {
                    frameSelect(cursor.x, cursor.y + 1);
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public Rectangle getDepthBounds(int depth) {
        return getFrameBounds(frame, depth);
    }

    public Rectangle getFrameBounds(int frame, int depth) {
        Rectangle rect = new Rectangle();
        rect.width = FRAME_WIDTH;
        rect.height = FRAME_HEIGHT;
        rect.x = frame * FRAME_WIDTH;
        rect.y = depth * FRAME_HEIGHT;
        return rect;
    }

    public void refresh() {
        int frameCount = timeline == null ? 0 : timeline.getFrameCount();
        int maxDepth = timeline == null ? 0 : timeline.getMaxDepth();
        Dimension dim = new Dimension(FRAME_WIDTH * frameCount + 1, FRAME_HEIGHT * (maxDepth + 1));
        setSize(dim);
        setPreferredSize(dim);
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;        
        refresh();
    }
}
