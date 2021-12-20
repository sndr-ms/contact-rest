package com.saneforce.contact_rest;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;

import org.osgi.util.tracker.BundleTracker;
import org.osgi.util.tracker.BundleTrackerCustomizer;

public class Activator implements BundleActivator {

	private MyBundleTracker bundleTracker;

	private static String getBundleState(Bundle bundle) {
		if (bundle == null) {
			return "null";
		}
		int type = bundle.getState();
		
		switch (type) {
			case Bundle.INSTALLED:
				return "INSTALLED";
			case Bundle.RESOLVED:
				return "RESOLVED";
			case Bundle.STARTING:
				return "Starting";
			case Bundle.STOPPING:
				return "STOPPING";
			case Bundle.UNINSTALLED:
				return "UNINSTALLED";
			default:
				return "unknown event type: " + type;
		}
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		
		bundleTracker = new MyBundleTracker(context, context.getBundle().getState(), null);
		bundleTracker.open();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		System.out.println("Stopping Bundle");
		bundleTracker.close();
		bundleTracker = null;
	}

	private static final class MyBundleTracker extends BundleTracker<Object> {

		public MyBundleTracker(BundleContext context, int stateMask,
				BundleTrackerCustomizer customizer) {
			super(context, stateMask, customizer);
		}

		public Object addingBundle(Bundle bundle, BundleEvent event) {
			
			print(bundle, event);
			return bundle;
		}

		private void print(Bundle bundle, BundleEvent event) {
			
			System.out.println("Bundle " + bundle.getSymbolicName() +", State: " + getBundleState(bundle));
		}
		
		public void removedBundle(Bundle bundle, BundleEvent event,Object object) {
			print(bundle, event);
		}
		
		public void modifiedBundle(Bundle bundle, BundleEvent event,Object object) {
			print(bundle, event);
		}
	}

}
