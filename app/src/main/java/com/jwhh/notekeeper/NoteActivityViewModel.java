package com.jwhh.notekeeper;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteActivityViewModel extends ViewModel {
    private static final String DEFAULT_COURSE_ID = "com.jwhh.notekeeper.DEFAULT_COURSE_ID";
    private static final String DEFAULT_NOTE_TEXT = "com.jwhh.notekeeper.DEFAULT_NOTE_TEXT";
    private static final String DEFAULT_NOTE_TITLE = "com.jwhh.notekeeper.DEFAULT_NOTE_TITLE";

    public String mDefaultCourseId;
    public String mDefaultNoteText;
    public String mDefaultNoteTitle;
    public boolean mIsNewViewModel = true;

    public void saveState(Bundle outState) {
        outState.putString(DEFAULT_COURSE_ID, mDefaultCourseId);
        outState.putString(DEFAULT_NOTE_TEXT, mDefaultNoteText);
        outState.putString(DEFAULT_NOTE_TITLE, mDefaultNoteTitle);
    }

    public void restoreState(Bundle inState) {
        mDefaultCourseId = inState.getString(DEFAULT_COURSE_ID);
        mDefaultNoteText = inState.getString(DEFAULT_NOTE_TEXT);
        mDefaultNoteTitle = inState.getString(DEFAULT_NOTE_TITLE);
    }
}
