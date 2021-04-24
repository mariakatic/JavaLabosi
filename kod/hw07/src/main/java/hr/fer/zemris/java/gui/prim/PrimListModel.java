package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PrimListModel implements ListModel<Integer> {
	
	private List<ListDataListener> promatraci;
	private List<Integer> mojiPodatci;
	
	public PrimListModel() {
		promatraci = new ArrayList<>();
		mojiPodatci = new ArrayList<>();
		mojiPodatci.add(1);
	}
	
	public void next() {
		
		int last = mojiPodatci.get(mojiPodatci.size()-1) + 1;
		for (int i = 2; i <= Math.ceil(Math.sqrt(last)); i++) {
			if (last % i == 0) {
				last++;
				i = 1;
			}
		}
		mojiPodatci.add(last);
		
		ListDataEvent e = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, mojiPodatci.size()-1, mojiPodatci.size()-1);
		for(ListDataListener l : promatraci) {
			l.intervalAdded(e);
		}
	}

	@Override
	public int getSize() {
		return mojiPodatci.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return mojiPodatci.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		promatraci.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		promatraci.remove(l);
	}
	
}
