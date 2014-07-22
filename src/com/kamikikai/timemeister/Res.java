package com.kamikikai.timemeister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Res {
    public static final String PREF_KEY = "preferenceTimer";
	public static final List<Integer> drawable;
	public static final List<Integer> drawableButton;
	public static final List<Integer> drawableButtonTapped;
	static {
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.button_main_bike);
		list.add(R.drawable.button_main_book);
		list.add(R.drawable.button_main_car);
		list.add(R.drawable.button_main_egg);
		list.add(R.drawable.button_main_fire);
		list.add(R.drawable.button_main_fryingpan);
		list.add(R.drawable.button_main_music);
		list.add(R.drawable.button_main_noodle);
		list.add(R.drawable.button_main_pan);
		list.add(R.drawable.button_main_pasta);
		list.add(R.drawable.button_main_pc);
		list.add(R.drawable.button_main_rice);
		list.add(R.drawable.button_main_sleep);
		list.add(R.drawable.button_main_study);
		list.add(R.drawable.button_main_teatime);
		list.add(R.drawable.button_main_train);
		list.add(R.drawable.button_main_tv);
		list.add(R.drawable.button_main_umbllera);
		list.add(R.drawable.button_main_walk);
		drawable = Collections.unmodifiableList(list);
	}
	static {
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.button_main_bike_tapped);
		list.add(R.drawable.button_main_book_tapped);
		list.add(R.drawable.button_main_car_tapped);
		list.add(R.drawable.button_main_egg_tapped);
		list.add(R.drawable.button_main_fire_tapped);
		list.add(R.drawable.button_main_fryingpan_tapped);
		list.add(R.drawable.button_main_music_tapped);
		list.add(R.drawable.button_main_noodle_tapped);
		list.add(R.drawable.button_main_pan_tapped);
		list.add(R.drawable.button_main_pasta_tapped);
		list.add(R.drawable.button_main_pc_tapped);
		list.add(R.drawable.button_main_rice_tapped);
		list.add(R.drawable.button_main_sleep_tapped);
		list.add(R.drawable.button_main_study_tapped);
		list.add(R.drawable.button_main_teatime_tapped);
		list.add(R.drawable.button_main_train_tapped);
		list.add(R.drawable.button_main_tv_tapped);
		list.add(R.drawable.button_main_umbllera_tapped);
		list.add(R.drawable.button_main_walk_tapped);
		drawableButtonTapped = Collections.unmodifiableList(list);
	}
	static {
		List<Integer> list = new ArrayList<Integer>();
		list.add(R.drawable.image_main_bike);
		list.add(R.drawable.image_main_book);
		list.add(R.drawable.image_main_car);
		list.add(R.drawable.image_main_egg);
		list.add(R.drawable.image_main_fire);
		list.add(R.drawable.image_main_fryingpan);
		list.add(R.drawable.image_main_music);
		list.add(R.drawable.image_main_noodle);
		list.add(R.drawable.image_main_pan);
		list.add(R.drawable.image_main_pasta);
		list.add(R.drawable.image_main_pc);
		list.add(R.drawable.image_main_rice);
		list.add(R.drawable.image_main_sleep);
		list.add(R.drawable.image_main_study);
		list.add(R.drawable.image_main_teatime);
		list.add(R.drawable.image_main_train);
		list.add(R.drawable.image_main_tv);
		list.add(R.drawable.image_main_umbllera);
		list.add(R.drawable.image_main_walk);
		drawableButton = Collections.unmodifiableList(list);
	}
}
