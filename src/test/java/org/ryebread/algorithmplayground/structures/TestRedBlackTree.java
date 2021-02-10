package org.ryebread.algorithmplayground.structures;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.ryebread.algorithmplayground.structures.tree.BinarySearchTree;
import org.ryebread.algorithmplayground.structures.tree.RedBlackTree;

public class TestRedBlackTree {

	@Test
	public void testSingleElementAddAndSearch() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		assertTrue(tree.find(5).equals(5));
	}

	@Test
	public void testMultipleElementAddAndSearch() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(10);
		assertTrue(tree.find(5).equals(5));
		assertTrue(tree.find(10).equals(10));
	}

	@Test
	public void testInOrderTraversal() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		for (int i = 1000; i > 0; i--) {
			tree.add(i);
		}
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testDeleteWithNoChildren() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		assertTrue(tree.delete(6));
		assertFalse(tree.delete(6));
		assertNull(tree.find(6));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testDeleteWithOneChild() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		tree.add(7);
		assertTrue(tree.delete(6));
		assertFalse(tree.delete(6));
		assertNull(tree.find(6));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testDeleteWithTwoChildren() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		tree.add(5);
		tree.add(4);
		tree.add(6);
		tree.add(7);
		tree.add(1);
		assertTrue(tree.delete(5));
		assertFalse(tree.delete(5));
		assertNull(tree.find(5));
		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void testRandomDeletes() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		for (int i = 1000; i > 0; i--) {
			tree.add(i);
		}

		Set<Integer> alreadyDeleted = new HashSet<Integer>();
		Random rand = new Random((new Date()).getTime());
		for (int i = 0; i < 300; i++) {
			int randInt = rand.nextInt(1000) + 1;
			while (alreadyDeleted.contains(randInt)) {
				randInt = rand.nextInt(1000) + 1;
			}
			alreadyDeleted.add(randInt);
			assertTrue(tree.delete(randInt));
			assertNull(tree.find(randInt));
		}

		List<Integer> elements = tree.all();
		for (int i = 0; i < elements.size() - 1; i++) {
			assertTrue(elements.get(i).intValue() <= elements.get(i + 1).intValue());
		}
	}

	@Test
	public void failingTest() {
		RedBlackTree<Integer> tree = new RedBlackTree<>();
		for (int i = 1000; i > 0; i--) {
			tree.add(i);
		}
		tree.delete(312);
		tree.delete(617);
		tree.delete(126);
		tree.delete(562);
		tree.delete(103);
		tree.delete(225);
		tree.delete(165);
		tree.delete(491);
		tree.delete(139);
		tree.delete(580);
		tree.delete(105);
		tree.delete(376);
		tree.delete(492);
		tree.delete(715);
		tree.delete(528);
		tree.delete(804);
		tree.delete(945);
		tree.delete(503);
		tree.delete(320);
		tree.delete(616);
		tree.delete(236);
		tree.delete(356);
		tree.delete(150);
		tree.delete(171);
		tree.delete(65);
		tree.delete(898);
		tree.delete(912);
		tree.delete(877);
		tree.delete(781);
		tree.delete(392);
		tree.delete(713);
		tree.delete(669);
		tree.delete(949);
		tree.delete(445);
		tree.delete(736);
		tree.delete(496);
		tree.delete(551);
		tree.delete(914);
		tree.delete(94);
		tree.delete(674);
		tree.delete(207);
		tree.delete(240);
		tree.delete(507);
		tree.delete(855);
		tree.delete(89);
		tree.delete(989);
		tree.delete(314);
		tree.delete(991);
		tree.delete(455);
		tree.delete(851);
		tree.delete(756);
		tree.delete(568);
		tree.delete(11);
		tree.delete(369);
		tree.delete(128);
		tree.delete(74);
		tree.delete(824);
		tree.delete(994);
		tree.delete(436);
		tree.delete(83);
		tree.delete(966);
		tree.delete(321);
		tree.delete(185);
		tree.delete(66);
		tree.delete(808);
		tree.delete(700);
		tree.delete(817);
		tree.delete(796);
		tree.delete(400);
		tree.delete(398);
		tree.delete(596);
		tree.delete(14);
		tree.delete(899);
		tree.delete(903);
		tree.delete(217);
		tree.delete(637);
		tree.delete(750);
		tree.delete(731);
		tree.delete(355);
		tree.delete(665);
		tree.delete(504);
		tree.delete(584);
		tree.delete(852);
		tree.delete(672);
		tree.delete(102);
		tree.delete(887);
		tree.delete(403);
		tree.delete(508);
		tree.delete(506);
		tree.delete(53);
		tree.delete(811);
		tree.delete(96);
		tree.delete(423);
		tree.delete(995);
		tree.delete(518);
		tree.delete(222);
		tree.delete(786);
		tree.delete(62);
		tree.delete(391);
		tree.delete(758);
		tree.delete(306);
		tree.delete(61);
		tree.delete(651);
		tree.delete(479);
		tree.delete(604);
		tree.delete(657);
		tree.delete(111);
		tree.delete(290);
		tree.delete(624);
		tree.delete(260);
		tree.delete(274);
		tree.delete(997);
		tree.delete(611);
		tree.delete(359);
		tree.delete(174);
		tree.delete(477);
		tree.delete(108);
		tree.delete(82);
		tree.delete(601);
		tree.delete(904);
		tree.delete(86);
		tree.delete(582);
		tree.delete(46);
		tree.delete(924);
		tree.delete(771);
		tree.delete(463);
		tree.delete(370);
		tree.delete(644);
		tree.delete(787);
		tree.delete(847);
		tree.delete(332);
		tree.delete(234);
		tree.delete(965);
		tree.delete(905);
		tree.delete(555);
		tree.delete(494);
		tree.delete(406);
		tree.delete(874);
		tree.delete(538);
		tree.delete(942);
		tree.delete(960);
		tree.delete(168);
		tree.delete(417);
		tree.delete(347);
		tree.delete(920);
		tree.delete(107);
		tree.delete(812);
		tree.delete(992);
		tree.delete(519);
		tree.delete(183);
		tree.delete(501);
		tree.delete(696);
		tree.delete(829);
		tree.delete(554);
		tree.delete(826);
		tree.delete(820);
		tree.delete(379);
		tree.delete(533);
		tree.delete(753);
		tree.delete(233);
		tree.delete(526);
		tree.delete(433);
		tree.delete(149);
		tree.delete(843);
		tree.delete(645);
		tree.delete(667);
		tree.delete(427);
		tree.delete(768);
		tree.delete(798);
		tree.delete(837);
		tree.delete(24);
		tree.delete(333);
		tree.delete(248);
		tree.delete(659);
		tree.delete(235);
		tree.delete(547);
		tree.delete(907);
		tree.delete(823);
		tree.delete(476);
		tree.delete(571);
		tree.delete(184);
		tree.delete(1);
		tree.delete(242);
		tree.delete(276);
		tree.delete(51);
		tree.delete(412);
		tree.delete(396);
		tree.delete(131);
		tree.delete(17);
		tree.delete(789);
		tree.delete(372);
		tree.delete(569);
		tree.delete(735);
		tree.delete(386);
		tree.delete(395);
		tree.delete(529);
		tree.delete(553);
		tree.delete(264);
		tree.delete(615);
		tree.delete(563);
		tree.delete(329);
		tree.delete(833);
		tree.delete(23);
		tree.delete(980);
		tree.delete(712);
		tree.delete(218);
		tree.delete(134);
		tree.delete(974);
		tree.delete(599);
		tree.delete(955);
		tree.delete(583);
		tree.delete(858);
		tree.delete(20);
		tree.delete(488);
		tree.delete(578);
		tree.delete(618);
		tree.delete(806);
		tree.delete(393);
		tree.delete(442);
		tree.delete(762);
		tree.delete(92);
		tree.delete(854);
		tree.delete(311);
		tree.delete(947);
		tree.delete(552);
		tree.delete(289);
		tree.delete(435);
		tree.delete(387);
		tree.delete(454);
		tree.delete(115);
		tree.delete(309);
		tree.delete(224);
		tree.delete(261);
		tree.delete(162);
		tree.delete(199);
		tree.delete(598);
		tree.delete(190);
		tree.delete(30);
		tree.delete(709);
		tree.delete(779);
		tree.delete(493);
		tree.delete(682);
		tree.delete(382);
		tree.delete(814);
		tree.delete(683);
		tree.delete(946);
		tree.delete(675);
		tree.delete(109);
		tree.delete(419);
		tree.delete(401);
		tree.delete(489);
		tree.delete(911);
		tree.delete(231);
		tree.delete(921);
		tree.delete(4);
		tree.delete(447);
		tree.delete(56);
		tree.delete(498);
		tree.delete(431);
		tree.delete(338);
		tree.delete(95);
		tree.delete(385);
		tree.delete(856);
	}
}
