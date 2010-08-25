/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 2007-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * Oracle and Java are registered trademarks of Oracle and/or its affiliates.
 * Other names may be trademarks of their respective owners.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License.  When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

package org.netbeans.modules.profiler.snaptracer.impl;

import java.awt.BorderLayout;
import org.netbeans.modules.profiler.snaptracer.impl.swing.VisibilityHandler;
import org.netbeans.modules.profiler.snaptracer.impl.timeline.TimelinePanel;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JToolBar;

/**
 *
 * @author Jiri Sedlacek
 */
final class TimelineView {

    private final TracerModel model;
    private TimelinePanel panel;

    private VisibilityHandler viewHandler;


    // --- Constructor ---------------------------------------------------------

    TimelineView(TracerModel model) {
        this.model = model;
    }


    // --- Internal interface --------------------------------------------------

    void reset() {
        if (panel != null) panel.reset();
    }

    void resetSelection() {
        if (panel != null) panel.resetSelection();
    }

    void updateActions() {
        if (panel != null) panel.updateActions();
    }

    Action zoomInAction() {
        if (panel != null) return panel.zoomInAction();
        return null;
    }

    Action zoomOutAction() {
        if (panel != null) return panel.zoomOutAction();
        return null;
    }

    Action toggleViewAction() {
        if (panel != null) return panel.toggleViewAction();
        return null;
    }

    AbstractButton mouseZoom() {
        if (panel != null) return panel.mouseZoom();
        return null;
    }

    AbstractButton mouseHScroll() {
        if (panel != null) return panel.mouseHScroll();
        return null;
    }

    AbstractButton mouseVScroll() {
        if (panel != null) return panel.mouseVScroll();
        return null;
    }


    void registerViewListener(VisibilityHandler viewHandler) {
        if (panel != null) {
            viewHandler.handle(panel);
        } else {
            this.viewHandler = viewHandler;
        }

    }

    boolean isShowing() {
        return panel != null && panel.isShowing();
    }

    // --- UI implementation ---------------------------------------------------

    JComponent getView() {
        panel = new TimelinePanel(model.getTimelineSupport());

        if (viewHandler != null) {
            viewHandler.handle(panel);
            viewHandler = null;
        }
        
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorderPainted(false);

        toolbar.add(panel.zoomInAction());
        toolbar.add(panel.zoomOutAction());
        toolbar.add(panel.toggleViewAction());
        toolbar.addSeparator();

        ButtonGroup bg = new ButtonGroup();
        AbstractButton mz = panel.mouseZoom();
        bg.add(mz);
        toolbar.add(mz);
        AbstractButton mh = panel.mouseHScroll();
        bg.add(mh);
        toolbar.add(mh);

        JPanel container = new JPanel(new BorderLayout());
        container.add(toolbar, BorderLayout.NORTH);
        container.add(panel, BorderLayout.CENTER);

        return container;
    }

}
