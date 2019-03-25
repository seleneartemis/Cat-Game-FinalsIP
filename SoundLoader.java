import java.io.File;
import sun.audio.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.FloatControl;



public class SoundLoader{
	
	private String musicFile;

	private AudioInputStream stream;
	private Clip clip;
	private FloatControl controlVolume;


	public SoundLoader(){

	}

	public void loadSound(String path){

		musicFile = path;
		playSoundEffect();
	}

	public void playBackGroundMusic(){

		try{

			stream = AudioSystem.getAudioInputStream(new File(musicFile));

			clip = AudioSystem.getClip();

			clip.open(stream);
			controlVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			controlVolume.setValue(6.02f);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);

			

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void adjustVolume(float volume){

		controlVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlVolume.setValue(volume);
	}

	public void playSoundEffect(){

		try{

			stream = AudioSystem.getAudioInputStream(new File(musicFile));

			clip = AudioSystem.getClip();

			clip.open(stream);
			clip.start();

			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public void stopSound(){
		clip.stop();
	}


}