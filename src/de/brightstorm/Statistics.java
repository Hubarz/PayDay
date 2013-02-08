package de.brightstorm;

import java.io.IOException;

public class Statistics {
	private static long paid=0;
    private static long items=0;
    
    private Metrics metrics;
    
    public Statistics(payday p) throws IOException {
        metrics = new Metrics(p);
        Metrics.Graph graph = metrics.createGraph("Reward type");
        if (payday.conf.isMoney()) graph.addPlotter(new Metrics.Plotter("Money")
        {
            public int getValue() {
                return 1;
            }
        }); else
        graph.addPlotter(new Metrics.Plotter("Item")
        {
            public int getValue() {
                return 1;
            }
        });
        Metrics.Graph graph2 = metrics.createGraph("Money paid");
        graph2.addPlotter(new Metrics.Plotter("$")
        {
            public int getValue() {
                return (int) paid;
            }
            
            public void reset() {
                paid = 0;
            }
        });
        graph2.addPlotter(new Metrics.Plotter("Items")
        {
            public int getValue() {
                return (int) items;
            }
            
            public void reset() {
            	items = 0;
            }
        });
        metrics.start();
    }
    
    public static void send() {
        
    }
    
    public void setPaid(long p) {
    	paid=p;
    }
    
    public void setItems(long i) {
    	items=i;
    }
}