package com.vaibhavlakhera.ppicalc;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AboutDialogFragment extends DialogFragment
{
	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.about_dialog, null);

		final SpannableString spannableString = new SpannableString(getString(R.string.about_dialog_text));
		Linkify.addLinks(spannableString, Linkify.ALL);

		TextView dialogTextView = (TextView) view.findViewById(R.id.about_dialog_text_view);
		dialogTextView.setMovementMethod(LinkMovementMethod.getInstance());
		dialogTextView.setText(spannableString);

		builder.setTitle(R.string.about_settings).setView(view);
		builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				dismiss();
			}
		});
		return builder.create();
	}
}
