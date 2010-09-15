package se.arnholm.rplogtool.shared;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Test;

import se.arnholm.rplogtool.server.LogCleaner;

import com.google.appengine.repackaged.org.joda.time.Duration;

public class LogCleanerTest {

	private String testLog = "[03:07:42]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"\n" +
	"[03:07:50]  Xerxis Rodenberger: Rizo's self esteem often exceeds his height\n" +
	"[03:07:53]  Silver Opaque: why of course\n" +
	"[03:07:53]  Xerxis Rodenberger chuckles\n" +
	"[03:08:09]  Silver Opaque: but some more so than other *glances around the bar*\n" +
	"[03:08:14]  serendipity Savira stays silent taking another sip from her glass\n" +
	"[03:08:55]  Ricard Collas is Online\n" +
	"[03:09:55]  Monfang Snowpaw sleans agains t the bar. \"I thought Rizo banned you, Silver.\"\n" +
	"[03:09:57]  Silver Opaque smiles evily\n" +
	"[03:10:01]  Nadine Nozaki's smile leaves her fance.\n" +
	"[03:10:07]  Ricard Collas is Offline\n" +
	"[03:10:13]  Nadine Nozaki: I thoulg you was going to excuse..\n" +
	"[03:10:25]  Ranith Strazytski is Offline\n" +
	"[03:10:29]  Monfang Snowpaw rolls his eyes and looks a tNadina. \"Look, shut up. If you want to kill me, then do it already.\"\n" +
	"[03:10:31]  CCS - MTR - 1.0.2: Silver Opaque has detached their meter\n" +
	"[03:10:35]  Samantha Linnaeus smiles, \"Hello Seren\"\n" +
	"[03:10:36]  serendipity Savira looks up and smiles \"hello Sam\"\n" +
	"[03:10:39]  CCS - MTR - 1.0.2: Nadine Nozaki uses Rupture-6 on Monfang Snowpaw\n" +
	"[03:10:39]  CCS - MTR - 1.0.2: Monfang Snowpaw has been damaged!\n" +
	"[03:10:40]  CCS - MTR - 1.0.2: Monfang Snowpaw loses life!\n" +
	"[03:10:40]  CCS - MTR - 1.0.2: Monfang Snowpaw has been defeated by Nadine Nozaki!\n" +
	"[03:10:48]  Nadine Nozaki does as orderd..\n" +
	"[03:10:54]  CCS - MTR - 1.0.2: You can use offensive skills again\n" +
	"[03:11:17]  serendipity Savira looks in shock as Nadine attacks Mon\n" +
	"[03:11:18]  Xerxis Rodenberger: Sh. Mistress. Drag him out before anyone notices\n" +
	"[03:11:24]  Monfang Snowpaw stands beck up.\n" +
	"[03:11:25]  Lensi Hax is Offline\n" +
	"[03:11:28]  Nadine Nozaki grabs the tails of the wolf\n" +
	"[03:11:37]  Samantha Linnaeus sees the sudden attack out of the corner of her eye, \"Goodness!\"\n" +
	"[03:11:41]  Monfang Snowpaw shakes his head. \"Really, you have to do better than that.\"\n" +
	"[03:11:49]  Nadine Nozaki stats pulling the wolf form the bar.\n" +
	"[03:12:19]  Nadine Nozaki: (( just follow please))\n" +
	"[03:12:38]  serendipity Savira pats the bar stool next to her and whispers to Sam, \"Come and sit\"\n" +
	"[03:12:47]  Samantha Linnaeus' eyes are wide, \"How have you been Seren?\"\n" +
	"[03:12:57]  CCS - MTR - 1.0.2: You have called for GM assistance\n" +
	"[03:12:59]  Silver Opaque turns and looks at samantha, \"hello there\" a twinkle plays in my eyes\n" +
	"[03:13:03]  Ricard Collas is Online\n" +
	"[03:13:25]  Rin Tae is Offline\n" +
	"[03:13:28]  Nadine Nozaki takes teh weposn form the wolf and tosses the body into the river\n" +
	"[03:13:42]  Xerxis Rodenberger nods excusingly to Sam:\"I'm sorry. Its usually not that violent here\"\n" +
	"[03:13:51]  Nadine Nozaki: Sorry about that\n" +
	"[03:13:58]  Wolfbringer Sixpack is Online\n" +
	"[03:14:18]  Monfang Snowpaw reterns. \"Sam. Run. This tavern is full of vampires!\"\n" +
	"[03:14:20]  Samantha Linnaeus smiles a nervous smile and replies, \"Yes, I understand\"\n" +
	"[03:14:32]  Silver Opaque: crazy wolf talking\n" +
	"[03:14:34]  Samantha Linnaeus: \"Vampires?\"\n" +
	"[03:14:53]  serendipity Savira nods at sam , hoping the others didnt notice\n" +
	"[03:14:53]  Samantha Linnaeus looks around the room in some confusion...\"\n";

	@Test
	public void testCleanLog() {
		
		String expected = "[03:07:42]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"\n" +
		"[03:07:50]  Xerxis Rodenberger: Rizo's self esteem often exceeds his height\n" +
		"[03:07:53]  Silver Opaque: why of course\n" +
		"[03:07:53]  Xerxis Rodenberger chuckles\n" +
		"[03:08:09]  Silver Opaque: but some more so than other *glances around the bar*\n" +
		"[03:08:14]  serendipity Savira stays silent taking another sip from her glass\n" +
		"[03:09:55]  Monfang Snowpaw sleans agains t the bar. \"I thought Rizo banned you, Silver.\"\n" +
		"[03:09:57]  Silver Opaque smiles evily\n" +
		"[03:10:01]  Nadine Nozaki's smile leaves her fance.\n" +
		"[03:10:13]  Nadine Nozaki: I thoulg you was going to excuse..\n" +
		"[03:10:29]  Monfang Snowpaw rolls his eyes and looks a tNadina. \"Look, shut up. If you want to kill me, then do it already.\"\n" +
		"[03:10:31]  CCS - MTR - 1.0.2: Silver Opaque has detached their meter\n" +
		"[03:10:35]  Samantha Linnaeus smiles, \"Hello Seren\"\n" +
		"[03:10:36]  serendipity Savira looks up and smiles \"hello Sam\"\n" +
		"[03:10:39]  CCS - MTR - 1.0.2: Nadine Nozaki uses Rupture-6 on Monfang Snowpaw\n" +
		"[03:10:39]  CCS - MTR - 1.0.2: Monfang Snowpaw has been damaged!\n" +
		"[03:10:40]  CCS - MTR - 1.0.2: Monfang Snowpaw loses life!\n" +
		"[03:10:40]  CCS - MTR - 1.0.2: Monfang Snowpaw has been defeated by Nadine Nozaki!\n" +
		"[03:10:48]  Nadine Nozaki does as orderd..\n" +
		"[03:10:54]  CCS - MTR - 1.0.2: You can use offensive skills again\n" +
		"[03:11:17]  serendipity Savira looks in shock as Nadine attacks Mon\n" +
		"[03:11:18]  Xerxis Rodenberger: Sh. Mistress. Drag him out before anyone notices\n" +
		"[03:11:24]  Monfang Snowpaw stands beck up.\n" +
		"[03:11:28]  Nadine Nozaki grabs the tails of the wolf\n" +
		"[03:11:37]  Samantha Linnaeus sees the sudden attack out of the corner of her eye, \"Goodness!\"\n" +
		"[03:11:41]  Monfang Snowpaw shakes his head. \"Really, you have to do better than that.\"\n" +
		"[03:11:49]  Nadine Nozaki stats pulling the wolf form the bar.\n" +
		"[03:12:19]  Nadine Nozaki: (( just follow please))\n" +
		"[03:12:38]  serendipity Savira pats the bar stool next to her and whispers to Sam, \"Come and sit\"\n" +
		"[03:12:47]  Samantha Linnaeus' eyes are wide, \"How have you been Seren?\"\n" +
		"[03:12:57]  CCS - MTR - 1.0.2: You have called for GM assistance\n" +
		"[03:12:59]  Silver Opaque turns and looks at samantha, \"hello there\" a twinkle plays in my eyes\n" +
		"[03:13:28]  Nadine Nozaki takes teh weposn form the wolf and tosses the body into the river\n" +
		"[03:13:42]  Xerxis Rodenberger nods excusingly to Sam:\"I'm sorry. Its usually not that violent here\"\n" +
		"[03:13:51]  Nadine Nozaki: Sorry about that\n" +
		"[03:14:18]  Monfang Snowpaw reterns. \"Sam. Run. This tavern is full of vampires!\"\n" +
		"[03:14:20]  Samantha Linnaeus smiles a nervous smile and replies, \"Yes, I understand\"\n" +
		"[03:14:32]  Silver Opaque: crazy wolf talking\n" +
		"[03:14:34]  Samantha Linnaeus: \"Vampires?\"\n" +
		"[03:14:53]  serendipity Savira nods at sam , hoping the others didnt notice\n" +
		"[03:14:53]  Samantha Linnaeus looks around the room in some confusion...\"\n";
		LogCleaner log = new LogCleaner(testLog);
		String cleaned = log.getClean();
		assertEquals(expected, cleaned);
		//		fail("Not yet implemented");
	}
	
	@Test
	public void testCleanLog2() {
		String text = "[03:07:42]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"\n" +
		"[03:14:53]  Samantha Linnaeus looks around the room in some confusion...\"\n";
		String expected = "[03:07:42]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"\n" +
		"[03:14:53]  Samantha Linnaeus looks around the room in some confusion...\"\n";
		LogCleaner log = new LogCleaner(text);
		String cleaned = log.getClean();
		assertEquals(expected, cleaned);
		//		fail("Not yet implemented");
	}
	
	@Test
	public void testDuration() {
		LogCleaner log = new LogCleaner(testLog);
		Duration expected = new Duration(431000);
		assertEquals(expected.getMillis(), log.getDuration().getMillis());
	}
	
	@Test
	public void testGetTime() {
		String line = "[03:07]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"";
		long time = LogCleaner.getTime(line);
		assertEquals(11220000, time);
	}
	
	@Test
	public void testPlayers() {
		LogCleaner log = new LogCleaner(testLog);
		Set<String> who = log.getPartisipants(); 
		System.out.println("Test:" + who);
		assertTrue("Nadine should be in set", who.contains("Nadine Nozaki"));
		assertEquals(11262000, log.getPlayerInfo("Nadine Nozaki").getFirstTime());
	}

}
