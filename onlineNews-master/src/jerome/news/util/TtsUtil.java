package jerome.news.util;

import android.content.Context;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;

public class TtsUtil {

	private static SpeechSynthesizer mTts;
	private static String voicer = "";
	private static TtsUtil single = null;

	public synchronized static TtsUtil getInstance(Context context) {
		if (single == null) {
			single = new TtsUtil();

			// ��ʼ���ϳɶ���
			mTts = SpeechSynthesizer.createSynthesizer(context, null);
			setParam();
		}
		return single;
	}

	/**
	 * ��������
	 * 
	 * @param param
	 * @return
	 */
	private static void setParam() {
		// ���úϳ�
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);

		// ���÷�����
		mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);

		// ��������
		mTts.setParameter(SpeechConstant.SPEED, "50");

		// ��������
		mTts.setParameter(SpeechConstant.PITCH, "50");

		// ��������
		mTts.setParameter(SpeechConstant.VOLUME, "50");

		// ���ò�������Ƶ������
		mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
	}

	/**
	 * ��ʼ����
	 * @param text
	 * @param mTtsListener
	 * @return
	 */
	public int startSpeak(String text,
			com.iflytek.cloud.SynthesizerListener mTtsListener) {
		if (mTts.isSpeaking()) {
			stopSpeak();
		}
		return mTts.startSpeaking(text, mTtsListener);
	}
	
	/**
	 * ֹͣ����
	 */
	public void stopSpeak() {
		if (null != mTts) {
			try {
				mTts.stopSpeaking();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
