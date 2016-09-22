package com.project.graphs.test;

import static org.junit.Assert.*;

import com.project.graphs.GraphPanel;
import org.junit.Before;
import org.junit.Test;

public class GraphTest {

    GraphPanel graphPanel;

    @Before
    public void testComponents() {
        graphPanel = new GraphPanel();
    }

    @Test
    public void test() {
        assertNotNull(graphPanel.getColorModel());
    }
}
