package se.arnholm.rplogtool.shared;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.Vector;

import org.junit.Test;

import se.arnholm.rplogtool.server.LogCleaner;

import org.joda.time.Duration;

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
	public void testDuration2() {
		LogCleaner log = new LogCleaner(log2);
		Duration expected = new Duration(1828000);
		assertEquals(expected.getMillis(), log.getDuration().getMillis());
	}	
	@Test
	public void testGetTime() {
		assertEquals(11220000, LogCleaner.getTime("[03:07]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\""));
		assertEquals(83103000, LogCleaner.getTime("[2010-09-15 23:05:03]  Xerxis Rodenberger: See you"));
		assertEquals(81275000, LogCleaner.getTime("[2010-09-15 22:34:35]  CCS - MTR - 1.0.2: Nadine Nozaki has entered a combative state\n"));
	}
	
	@Test
	public void testPlayers() {
		LogCleaner log = new LogCleaner(testLog);
		Set<String> who = log.getPartisipants(); 
//		System.out.println("Test:" + who);
		assertTrue("Nadine should be in set", who.contains("Nadine Nozaki"));
		assertEquals(11262000, log.getPlayerInfo("Nadine Nozaki").getFirstTime());
		assertEquals(11631000, log.getPlayerInfo("Nadine Nozaki").getLastTime());
		Duration d = log.getPlayerInfo("Nadine Nozaki").getDuration();
		assertEquals(369000, d.getMillis());
		System.out.println("Nads: " + d.toPeriod().getHours() + ":" + d.toPeriod().getMinutes() + " "
				+ log.getPlayerInfo("Nadine Nozaki").getLines());
		assertEquals(10, log.getPlayerInfo("Nadine Nozaki").getNumberOfLines());	
	}
	@Test
	public void testTimeFormat() {
		LogCleaner log = new LogCleaner(testLog);
		assertEquals("0:06", LogCleaner.formatTime(log.getPlayerInfo("Nadine Nozaki").getDuration()));
	}
	
	@Test
	public void testGetPLayerName() {
		assertEquals("Nadine Nozaki",
				LogCleaner.getPlayerName("[03:07:42]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"\n"));
		assertEquals("Nadine Nozaki",
				LogCleaner.getPlayerName("[03:10:01]  Nadine Nozaki's smile leaves her fance.\n"));
		assertEquals("Nadine Nozaki",
				LogCleaner.getPlayerName("[03:10:13]  Nadine Nozaki: I thoulg you was going to excuse..\n"));
	}
	
	@Test
	public void testLineSplit() {
		helpTestLineSplit("[03:07]  Nadine Nozaki ndos, \"Aint we all but Xerx human?\"", 
				"03:07", "Nadine Nozaki", " ndos, \"Aint we all but Xerx human?\"");
		helpTestLineSplit("[2010-09-15 23:05:03]  Xerxis Rodenberger: See you", 
				"2010-09-15 23:05:03", "Xerxis Rodenberger", ": See you");
		
		helpTestLineSplit("[2010-09-15 22:34:35]  CCS - MTR - 1.0.2: Nadine Nozaki has entered a combative state",
				"2010-09-15 22:34:35", "Nadine Nozaki"," has entered a combative state");
	}

	private void helpTestLineSplit(String line, String time, String name, String pose) {
		Vector<String> s1 = LogCleaner.splitLine(line);
		assertNotNull("Unable to split: " + line, s1);
		assertEquals(time, s1.elementAt(0));
		assertEquals(name, s1.elementAt(1));
		assertEquals(pose, s1.elementAt(2));
	}
	
	String log2 = 
		"[2010-09-15 22:34:35]  CCS - MTR - 1.0.2: Nadine Nozaki has entered a combative state\n" +
"[2010-09-15 22:34:40]  CCS - MTR - 1.0.2: Nadine Nozaki has entered a non-combative state\n" +
"[2010-09-15 22:34:57]  BeoWulf Foxtrot is Offline\n" +
"[2010-09-15 22:35:15]  Selene Weatherwax is Offline\n" +
"[2010-09-15 22:35:24]  Jo Soosung is Offline\n" +
"[2010-09-15 22:36:05]  Rondevous Giano is Offline\n" +
"[2010-09-15 22:37:21]  BeoWulf Foxtrot is Online\n" +
"[2010-09-15 22:39:31]  Kommandant Epin is Offline\n" +
"[2010-09-15 22:39:33]  CCS - MTR - 1.0.2: Nadine Nozaki has entered a combative state\n" +
"[2010-09-15 22:39:41]  Nadine Nozaki steals a wet kiss\n" +
"[2010-09-15 22:39:52]  Erosid Dryke is Online\n" +
"[2010-09-15 22:40:13]  Selene Weatherwax is Online\n" +
"[2010-09-15 22:42:17]  BeoWulf Foxtrot is Offline\n" +
"[2010-09-15 22:45:30]  BeoWulf Foxtrot is Online\n" +
"[2010-09-15 22:47:20]  MoonGypsy Writer is Offline\n" +
"[2010-09-15 22:48:50]  Skye Hanfoi is Offline\n" +
"[2010-09-15 22:49:15]  KittyDarling Zelnik is Offline\n" +
"[2010-09-15 22:49:24]  Traven Sachs is Offline\n" +
"[2010-09-15 22:49:49]  McCabe Maxsted is Offline\n" +
"[2010-09-15 22:50:08]  Valmont1985 Radek is Offline\n" +
"[2010-09-15 22:50:26]  Xerxis Rodenberger kisses back passionately\n" +
"[2010-09-15 22:50:34]  Skye Hanfoi is Online\n" +
"[2010-09-15 22:50:40]  Voodoo Halostar is Offline\n" +
"[2010-09-15 22:51:10]  Voodoo Halostar is Online\n" +
"[2010-09-15 22:53:57]  Imogen Aeon is Offline\n" +
"[2010-09-15 22:54:29]  Pethonia Baxton is Offline\n" +
"[2010-09-15 22:57:26]  Nadine Nozaki: OMG OMG\n" +
"[2010-09-15 22:57:33]  Nadine Nozaki: Drama?\n" +
"[2010-09-15 22:57:33]  Nadine Nozaki: OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG! OMG!\n" +
"[2010-09-15 22:57:56]  Xerxis Rodenberger: Whats wrong?\n" +
"[2010-09-15 22:58:01]  Nadine Nozaki: RUN TIME\n" +
"[2010-09-15 22:58:05]  Xerxis Rodenberger: *cries*\n" +
"[2010-09-15 22:58:06]  Nadine Nozaki: Serius wrong imho\n" +
"[2010-09-15 22:58:28]  Sparklin Indigo is Online\n" +
"[2010-09-15 22:58:44]  Xerxis Rodenberger: mmhmm\n" +
"[2010-09-15 22:59:02]  Wezab Ember is Online\n" +
"[2010-09-15 23:00:08]  Alix Lectar is Offline\n" +
"[2010-09-15 23:01:08]  Nadine Nozaki: Love you my girl,\n" +
"[2010-09-15 23:01:24]  Xerxis Rodenberger: Love you Mistress. Have a great day\n" +
"[2010-09-15 23:01:41]  Nadine Nozaki: u 2 :D\n" +
"[2010-09-15 23:02:06]  Xerxis Rodenberger kisses and snuggles\n" +
"[2010-09-15 23:02:17]  Gaea Singh is Offline\n" +
"[2010-09-15 23:03:22]  Gino Byron is Online\n" +
"[2010-09-15 23:04:47]  Nadine Nozaki: See you tonight love\n" +
"[2010-09-15 23:04:53]  Nadine Nozaki: I hgave to get going...\n" +
"[2010-09-15 23:05:03]  Xerxis Rodenberger: See you\n";

}
