package com.aainc.recyclebin.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

// Toasting from background processes using a Handler
public class Toa {
	private Context con;
	private Handler handler;

	public Toa(Context _context) {
		this.con = _context;
		this.handler = new Handler();
	}

	// toasts in separate thread
	private void runRunnable(final Runnable r) {
		Thread thread = new Thread() {
			public void run() {
				handler.post(r);
			}
		};

		thread.start();
		thread.interrupt();
		thread = null;
	}

	public void showToast(final String text, final int duration) {
		final Runnable runnable = new Runnable() {
			@Override
			public void run() {
				Toast.makeText(con, text, duration).show();
			}
		};

		runRunnable(runnable);
	}
}
