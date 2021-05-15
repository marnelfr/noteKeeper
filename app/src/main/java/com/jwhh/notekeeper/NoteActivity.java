package com.jwhh.notekeeper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class NoteActivity extends AppCompatActivity {

    //    private AppBarConfiguration appBarConfiguration;
//    private ActivityNoteBinding binding;
    public static final String NOTE_POSITION = "com.jwhh.notekeeper.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private Boolean mIsNewNote;
    private Spinner mSpinnerCourses;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private boolean mIsCancelling;
    private int mNotePosition;
    private NoteActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ViewModelProvider viewModelProvider = new ViewModelProvider(
            getViewModelStore(),
            ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
        );
        mViewModel = viewModelProvider.get(NoteActivityViewModel.class);


        mSpinnerCourses = findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        ArrayAdapter<CourseInfo> adapterCourses = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                courses
        );
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCourses.setAdapter(adapterCourses);

        readDisplayStateValues();
        saveDefaultValues();

        mTextNoteTitle = findViewById(R.id.text_note_title);
        mTextNoteText = findViewById(R.id.text_note_text);

        if (!mIsNewNote)
            displayNotes(mSpinnerCourses, mTextNoteTitle, mTextNoteText);
    }

    private void saveDefaultValues() {
        mViewModel.mDefaultCourseId = mNote.getCourse().getCourseId();
        mViewModel.mDefaultNoteText = mNote.getText();
        mViewModel.mDefaultNoteTitle = mNote.getTitle();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mIsCancelling) {
            if (mIsNewNote) {
                DataManager.getInstance().removeNote(mNotePosition);
            } else {
                restoreDefaultValues();
            }
        } else {
            saveChanges();
        }
    }

    private void restoreDefaultValues() {
        CourseInfo course = DataManager.getInstance().getCourse(mViewModel.mDefaultCourseId);
        mNote.setCourse(course);
        mNote.setTitle(mViewModel.mDefaultNoteTitle);
        mNote.setText(mViewModel.mDefaultNoteText);
    }

    private void saveChanges() {
        mNote.setCourse((CourseInfo) mSpinnerCourses.getSelectedItem());
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());
    }

    /**
     * Use to populate our Activity UI
     *
     * @param spinnerCourses
     * @param textNoteTitle
     * @param textNoteText
     */
    private void displayNotes(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        int courseIndex = courses.indexOf(mNote.getCourse());
        spinnerCourses.setSelection(courseIndex);
        textNoteTitle.setText(mNote.getTitle());
        textNoteText.setText(mNote.getText());
    }

    /**
     * To extract all of our extra from the intent that start this activity
     */
    private void readDisplayStateValues() {
        Intent intent = getIntent();
        mNotePosition = intent.getIntExtra(NOTE_POSITION, POSITION_NOT_SET);
        mIsNewNote = mNotePosition == POSITION_NOT_SET;
        if (mIsNewNote) {
            createNewNote();
        } else {
            mNote = DataManager.getInstance().getNotes().get(mNotePosition);
        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        mNote = dm.getNotes().get(mNotePosition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_note, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_email) {
            sendEmail();
            return true;
        } else if (id == R.id.action_cancel) {
            mIsCancelling = true;
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    private void sendEmail() {
        String subject = mTextNoteTitle.getText().toString();
        CourseInfo courseInfo = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String body = "Please take a look at what I discover in the course \"" + courseInfo.getTitle() +
                "\" at PluralSight.com.\n" + mTextNoteText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
                .putExtra(Intent.EXTRA_TEXT, body);
        startActivity(intent);
    }

//    @Override
//    public boolean onSupportNavigateUp() {
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        return NavigationUI.navigateUp(navController, appBarConfiguration)
//                || super.onSupportNavigateUp();
//    }
}