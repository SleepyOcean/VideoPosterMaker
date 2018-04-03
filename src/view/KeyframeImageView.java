package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/** 
 * @name: KeyframeImageView.java
 * @author:  sleepyocean
 * @date: created in 2018年3月23日 下午3:26:32 
 * @version: 1.0 
 * @description: TODO (write your description) 
 */

public class KeyframeImageView extends ImageView {
	private int index;

	public KeyframeImageView(Image image) {
		super(image);
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
