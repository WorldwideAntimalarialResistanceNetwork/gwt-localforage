package us.storee.gwt.libs.localforage.client;

import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.ScriptInjector;
import com.google.gwt.core.shared.GWT;

public class LocalForage {

	static {
		if (!isLoaded()) {
			load();
		}
	}

    static <T> void fireCallback(LocalForageCallback successCallback, Boolean err, T value) {
		if (successCallback == null) {
			return;
		}
		if (err == null) err = false;
        if(value instanceof String && value.equals("null")) value = null;
		successCallback.onComplete(err, value);
	}

    static void  fireInteratorCallback(LocalForageIteratorCallback localForageIteratorCallback, String value, String key, String iterationNumber) {
        if(value instanceof String && value.equals("null")) value = null;
        localForageIteratorCallback.iteratorCallback(value, key, Integer.valueOf(iterationNumber));
	}

    static void fireCallbackString(LocalForageCallback successCallback, Boolean err, String value) {
		fireCallback(successCallback, null, (value));
	}
    static void fireCallbackInt(LocalForageCallback successCallback, Boolean err, String value) {
		fireCallback(successCallback, null, Integer.parseInt(value));
	}

    static void fireCallbackArrayStr(LocalForageCallback successCallback, Boolean err, JsArrayString values) {
		fireCallback(successCallback, null, toArray(values));
	}

    static void fireCallback(LocalForageCallback successCallback) {
		fireCallback(successCallback, null, new String());
	}

    /**
     *  From JSArrays implementation https://code.google.com/p/gwt-in-the-air/source/browse/trunk/src/net/ltgt/gwt/jscollections/client/JsArrays.java
     * @param values
     * @return
     */
    public static String[] toArray(JsArrayString values) {
        if (GWT.isScript()) {
            return reinterpretCast(values);
        } else {
            int length = values.length();
            String[] ret = new String[length];
            for (int i = 0, l = length; i < l; i++) {
                ret[i] = values.get(i);
            }
            return ret;
        }
    }

    /**
     *  From JSArrayString implementation https://code.google.com/p/gwt-in-the-air/source/browse/trunk/src/net/ltgt/gwt/jscollections/client/JsArrays.java
     * @param value
     * @return
     */
    private static native String[] reinterpretCast(JsArrayString value) /*-{ return value; }-*/;

    /*
     * Data API
     -getItem(key, successCallback)
     -setItem(key, value, successCallback)
     -removeItem(key, successCallback)
     -clear(successCallback)
     -length(successCallback)
     -key(keyIndex, successCallback)
     -keys(successCallback)
     iterate(iteratorCallback, successCallback)
     */
    
    public native void getItem(String key, LocalForageCallback successCallback) /*-{
        var successCallbackFn = $entry(function(err, val) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackString(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Ljava/lang/String;)(successCallback, err, val)
        });
        $wnd.localforage.getItem(key, successCallbackFn);
    }-*/;
    
	public native void setItem(String key, String value, LocalForageCallback successCallback) /*-{
		var successCallbackFn = $entry(function(err, val) {
			@us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackString(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Ljava/lang/String;)(successCallback, err, val);
		});
		$wnd.localforage.setItem(key, value, successCallbackFn);
	}-*/;

	public native void removeItem(String key, LocalForageCallback successCallback) /*-{
        var successCallbackFn = $entry(function(err, val) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackString(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Ljava/lang/String;)(successCallback, err, val)
        });
        $wnd.localforage.removeItem(key, successCallbackFn);
    }-*/;

	public native void clear(LocalForageCallback successCallback) /*-{
        var successCallbackFn = $entry(function(val) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallback(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;)(successCallback);
        });
        $wnd.localforage.clear(successCallbackFn);
    }-*/;

	public native void length(LocalForageCallback<Integer> successCallback) /*-{
        var successCallbackFn = $entry(function(err, numberOfKeys) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackInt(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Ljava/lang/String;)(successCallback, err, numberOfKeys+'');
        });
        $wnd.localforage.length(successCallbackFn);
    }-*/;

    public native void key(Integer keyIndex,LocalForageCallback<String> successCallback) /*-{
        var successCallbackFn = $entry(function(err, keyName) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackString(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Ljava/lang/String;)(successCallback, err, keyName+'');
        });
        $wnd.localforage.key(keyIndex, successCallbackFn);
    }-*/;

    public native void keys(LocalForageCallback<String[]> successCallback) /*-{
        var successCallbackFn = $entry(function(err, keys) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackArrayStr(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Lcom/google/gwt/core/client/JsArrayString;)(successCallback, err, keys);
        });
        $wnd.localforage.keys(successCallbackFn);
    }-*/;

    public native void iterate(LocalForageIteratorCallback localForageIteratorCallback,LocalForageCallback<String[]> successCallback) /*-{
        var iteratorCallbackFn = $entry(function(value, key, iterationNumber) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireInteratorCallback(Lus/storee/gwt/libs/localforage/client/LocalForageIteratorCallback;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(localForageIteratorCallback, value, key, iterationNumber+"");
        });

        var successCallbackFn = $entry(function(err, keys) {
            @us.storee.gwt.libs.localforage.client.LocalForage::fireCallbackArrayStr(Lus/storee/gwt/libs/localforage/client/LocalForageCallback;Ljava/lang/Boolean;Lcom/google/gwt/core/client/JsArrayString;)(successCallback, err, keys);
        });
        $wnd.localforage.iterate(iteratorCallbackFn, successCallbackFn);
    }-*/;

	public native boolean isSupported() /*-{
		return !($wnd.localforage === null || typeof $wnd.localforage === "undefined");
	}-*/;
	

	/**
	 * Loads the offline library. You normally never have to do this manually
	 */
	public static void load() {
		if (!isLoaded()) {
			ScriptInjector.fromString(LocalForageResources.INSTANCE.js().getText()).setWindow(ScriptInjector.TOP_WINDOW).inject();
		}
	}
	
	public static native boolean isLoaded()/*-{
	if (typeof $wnd.localforage === "undefined" || $wnd.localforage === null) {
		return false;
	}
	return true;
}-*/;

}
