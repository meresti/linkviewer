/*
 * Copyright (c) 2015. meresti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package meresti.linkviewer.core.services.impl;

import meresti.linkviewer.core.entities.ContentRoom;
import meresti.linkviewer.core.entities.Link;
import meresti.linkviewer.core.exceptions.ObjectAlreadyExistsException;
import meresti.linkviewer.core.exceptions.ObjectNotFoundException;
import meresti.linkviewer.core.repositories.ContentRoomRepository;
import meresti.linkviewer.core.services.ContentRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ContentRoomServiceImpl implements ContentRoomService {

    private static final List<Link> LINKS = Arrays.asList(
            new Link(BigInteger.ONE,
                    "http://www.nevillehobson.com/2014/09/09/transparent-wearable-technology-within-enterprise/",
                    "http://www.nevillehobson.com/wp-content/uploads/closeup1-3201.jpg",
                    "How transparent is wearable technology within the enterprise? - NevilleHobson.com",
                    "In July, I took part in a public debate at the House of Commons about ethics in PR and wearable technology. Organized by The Debating Group and sponsored by the CIPR, the debate served a highly useful purpose of bringing a timely topic to front of mind amongst a community of communicators which considered the …"),
            new Link(new BigInteger("2"),
                    "http://en.wikipedia.org/wiki/Wearable_technology",
                    "http://bits.wikimedia.org/images/wikimedia-button.png",
                    "Wearable technology - Wikipedia, the free encyclopedia",
                    "Wearable technology, wearables, fashionable technology, wearable devices, tech togs, or fashion electronics are clothing and accessories incorporating computer and advanced electronic technologies. Th"),
            new Link(new BigInteger("3"),
                    "http://www.gizmodo.com.au/2015/01/these-augmented-reality-glasses-are-james-bond-worthy/",
                    "http://i.kinja-img.com/gawker-media/image/upload/t_xlarge/f5qeo8wlj4qtpeu4wppd.jpg",
                    "These Augmented Reality Glasses Are James Bond-Worthy",
                    "Ever heard of Ralph Osterhout? He’s known as the real-life “Q”. He created underwater vehicles featured in two James Bond movies. He..."),
            new Link(new BigInteger("4"),
                    "http://www.socialfresh.com/content-curation-scott-monty/",
                    "http://www.socialfresh.com/content/uploads/2015/02/5-content-curation-secrets-from-scott-monty-134x134.png",
                    "5 Content Curation Secrets From Scott Monty",
                    "Content curation is a game of numbers and filtering. To do it well, to be a depended upon news source for an audience, it also takes an attention to detail and consistency. Making sure the content you find and share is..."),
            new Link(new BigInteger("5"),
                    "http://www.rottentomatoes.com/m/the_amazing_spider_man_2/",
                    "http://resizing.flixster.com/8o3Z57xCEagHAkwEGRvhzyMFrH0=/800x1200/dkpu1ddg7pbsk.cloudfront.net/movie/11/18/00/11180032_ori.jpg",
                    "The Amazing Spider-Man 2",
                    "Critics Consensus: While the cast is outstanding and the special effects are top-notch, the latest installment of the Spidey saga suffers from an unfocused narrative and an overabundance of characters."),
            new Link(new BigInteger("6"),
                    "http://www.rottentomatoes.com/m/captain_america_the_winter_soldier_2014/",
                    "http://resizing.flixster.com/LBvcxGaeGVRboDUr1PHf9vMNe70=/405x600/dkpu1ddg7pbsk.cloudfront.net/movie/11/17/72/11177246_ori.jpg",
                    "Captain America: The Winter Soldier",
                    "Critics Consensus: Suspenseful and politically astute, Captain America: The Winter Soldier is a superior entry in the Avengers canon and is sure to thrill Marvel diehards."),
            new Link(new BigInteger("7"),
                    "http://m.huffpost.com/us/entry/7264904",
                    "http://i.huffpost.com/gen/1464944/images/o-US_UK_CA-facebook.jpg",
                    "Your Clothes Are Still Made In China But Now They Are Wearables",
                    "Just like any other trip, I fretted a bit over what to pack, what to wear? After all, Hong Kong is a fashion mecca, shopping destination and everyone is wearing the latest trends. What was everyone wearing this season in Hong Kong? Wearable technology, that's what."),
            new Link(new BigInteger("8"),
                    "http://www.scoop.it/t/data-nerd-s-corner/p/4028567073/2014/09/23/the-platform-problem-why-big-data-outcomes-still-aren-t-real-time",
                    "http://img.scoop.it/1ApTH5As4WMh4VFAVAq2moXXXL4j3HpexhjNOf_P3Yk8eJwghM0vysm9vt0-fRFi",
                    "The Platform Problem: Why Big Data Outcomes Sti...",
                    "And when these big data platforms perfectly connect these dots, we’ll see the surge in need for data scientists and chief data officers dwindle. Instead, these data-minded folks will find work within the companies building these platforms and technologies themselves. After all, that is truly wher..."),
            new Link(new BigInteger("9"),
                    "http://www.scoop.it/t/data-nerd-s-corner/p/4028545623/2014/09/23/rogue-big-data-projects-running-wild-across-businesses",
                    "http://img.scoop.it/pB_psTu-PfmYg7peIyYsIoXXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
                    "Rogue big data projects running wild across bus...",
                    "Dorney said it was a misconception to think big data analysis is for the big end of town as all types of organisations can take advantage. “Storing and accessing data is available to all. Making a start with data collection is what counts, even if you’re not quite sure why and how you’ll use t..."),
            new Link(BigInteger.TEN,
                    "http://www.businessinsider.com/i-was-assaulted-for-wearing-google-glass-2014-4",
                    "http://static1.businessinsider.com/image/5349ab1b6da811b458a1d22e/i-was-assaulted-for-wearing-google-glass-in-the-wrong-part-of-san-francisco.jpg",
                    "I Was Assaulted For Wearing Google Glass In The Wrong Part Of San Francisco",
                    "It happened in an instant."),
            new Link(new BigInteger("11"),
                    "http://sco.lt/5zaxv7",
                    "http://img.scoop.it/2oV5EsP4AvFcAStt3d6qhIXXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
                    "Real-Time Revenue and Big Data | SmartData Coll...",
                    "Big Data Pitfalls For those ready to dive into a Big Data implementation, be sure to weigh the pros and cons. According to a recent report by research firm Frost & Sullivan, three of the most common problems in big data deployments are incomplete data collection, false starts, and disruptive dra..."),
            new Link(new BigInteger("12"),
                    "http://patriot-tech.com/wearable-technology-trends-2014/",
                    "http://patriot-tech.com/wp-content/uploads/2014/06/wearable-technology-man-300x248.jpg",
                    "Wearable Technology Trends 2014 - Where to Go With Wearables",
                    "Wearable technology 2014 - It's almost impossible to pick up a magazine without running into the latest tech trend - wearables. See what the buzz is about"),
            new Link(new BigInteger("13"),
                    "http://renanablog.com/tag/google-glass/",
                    "http://d13pix9kaak6wt.cloudfront.net/avatar/users/r/a/p/raphaelfreeman_1395225982_42.png",
                    "google glass | The Renana Blog",
                    "Main menu When my father passed away recently I inherited his gold watch. Like most watches, it tells the time. The design is timeless and despite the fact that my father received this for his wedding"),
            new Link(new BigInteger("14"),
                    "http://www.tnooz.com/article/big-data-not-messiah/",
                    "http://www.tnooz.com/wp-content/uploads/2014/10/messiah-technology.jpg",
                    "Big Data is not the Messiah coming to save travel personalisation",
                    "Over the last year I’ve exposed myself to a lot of Big Data blogs, articles, conferences, webinars, technologies, chats over beers, and industry investments."),
            new Link(new BigInteger("15"),
                    "http://fredlybrand.com/2014/04/15/wearable-technology-and-the-state-of-the-art-in-fabrics/",
                    "http://fredlybranddotcom.files.wordpress.com/2014/04/1-fullscreen-capture-4122014-60152-am.jpg?w=300",
                    "Wearable Technology and the State of the Art in Fabrics",
                    "Wearable technology, as defined by Wikipedia to the right, integrates electronics into a wearable item, often into conventional apparel. There is often an electronic device, housed in hard plastic ..."),
            new Link(new BigInteger("16"),
                    "http://www.iflscience.com/technology/new-type-computer-capable-calculating-640tbs-data-one-billionth-second-could",
                    "http://www.iflscience.com/sites/www.iflscience.com/files/blog/%5Bnid%5D/HP_the-machine.jpg",
                    "New Type Of Computer Capable Of Calculating 640TBs Of Data In One Billionth Of A Second, Could Revolutionize Computing",
                    "Let me introduce The Machine- HP’s latest invention that could revolutionize the computing world. According to HP, The Machine is not a server, workstation, PC, device or phone but an amalgamation of all these things."),
            new Link(new BigInteger("17"),
                    "http://fortune.com/2014/06/13/these-big-data-companies-are-ones-to-watch/",
                    "https://fortunedotcom.files.wordpress.com/2014/06/104821290.jpg?quality=80&w=820&h=570&crop=1",
                    "These big data companies are ones to watch",
                    "Which companies are breaking new ground with big data technology? We ask 10 industry experts."),
            new Link(new BigInteger("18"),
                    "http://www.scoop.it/t/data-nerd-s-corner/p/4028192211/2014/09/17/top-cities-and-other-demographics-for-data-scientists",
                    "http://img.scoop.it/LGgZBIqib2AlhMbUiqb9P4XXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
                    "Top Cities and Other Demographics for Data Scie...",
                    "This analysis shows the distribution of data scientists per country, city, gender and company. It is based on Data Science Central (DSC) member database, only…"),
            new Link(new BigInteger("19"),
                    "http://www.forbes.com/fdc/welcome_mjx_mobile.html", null,
                    "Welcome to Forbes",
                    "Forbes Continue » Thought Of The Day ADVERTISEMENT "),
            new Link(new BigInteger("20"),
                    "http://www.slate.com/blogs/xx_factor/2014/09/02/jennifer_lawrence_and_other_celebrity_hacking_victims_should_not_have_to.html",
                    "http://www.slate.com/content/dam/slate/blogs/xx_factor/2014/09/02/jennifer_lawrence_and_other_celebrity_hacking_victims_should_not_have_to/162636595-jennifer-lawrence-carrying-her-oscar-for-best-actress.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg",
                    "“Don't Take Nude Selfies,” Shrug It Off, and Other Gross Advice for Hacked Celebs",
                    "On Sunday, dozens of nude photographs of female celebrities—including Jennifer Lawrence, Kate Upton, and Kirsten Dunst—were leaked online. The hacking and nonconsensual publication of these photos—which were kept privately by the women themselves and shared only with partners and friends of their choosing, if with anyone at all—is both a..."),
            new Link(new BigInteger("21"),
                    "http://www.vanityfair.com/style/scandal/2014/09/Monica-Lewinsky-photo-hacking-scandal",
                    "http://photos.vanityfair.com/2015/01/29/54cac41cb8f23e3a0315af35_image.png",
                    "Nude Traffic: When Have We Crossed the Double Yellow Line?",
                    "For all of our Instagram-enabled narcissism these days, there is no small degree of assault involved in having our private thoughts, conversations, and photos dished up for the entertainment of the masses."),
            new Link(new BigInteger("22"),
                    "http://www.dailymail.co.uk/tvshowbiz/article-3084093/I-don-t-need-little-boy-Paris-Hilton-strips-sexy-lingerie-steamy-new-music-video-High-Love.html",
                    "http://i.dailymail.co.uk/i/pix/2015/05/16/04/28BBF01700000578-0-image-a-75_1431746080972.jpg",
                    "Paris Hilton cavorts in lingerie in new music video High Off My Love",
                    "The 34-year-old heiress strips down to her lingerie to show off her taut physique in the sexy video, where she breathily sings, 'I don't need a little boy'."),
            new Link(new BigInteger("23"),
                    "http://www.theguardian.com/music/2015/may/18/taylor-swift-winner-calvin-harris-2015-billboard-music-awards",
                    "http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/18/1431933821856/d738a38a-97fa-4739-a914-cbdd84c8c75c-2060x1236.jpeg",
                    "Taylor Swift is big winner at 2015 Billboard music awards",
                    "The pop star swept the board at this year’s ceremony in Las Vegas, taking home a total of eight prizes"),
            new Link(new BigInteger("24"),
                    "http://www.bbc.com/news/world-africa-32765128",
                    "http://ichef.bbci.co.uk/news/1024/media/images/83041000/jpg/_83041947_027231635.jpg",
                    "Burundi crackdown after failed coup against Nkurunziza - BBC News",
                    "Eighteen people appear in court in Burundi as the government begins a crackdown against those suspected of involvement in a failed coup."),
            new Link(new BigInteger("25"),
                    "http://rt.com/news/261385-us-hunts-russian-citizens/",
                    "http://rt.com/files/news/3f/d0/90/00/us-hunts-russian-citizens.jpg",
                    "‘US hunts for Russians’: Moscow warns citizens traveling abroad",
                    "Russia has urged its citizens to weigh all the risks before traveling abroad, warning that the US is on a global “hunt” for Russian nationals, according to the latest statement published by the Foreign Ministry."),
            new Link(new BigInteger("26"),
                    "http://yisbe.tk/",
                    "http://i.perezhilton.com/wp-content/uploads/2012/02/adele-spends-second-week-on-top-of-billboard-web__oPt.jpg",
                    "Adele Sets Fire To The Billboard Charts For The Second Week In A Row",
                    "Adele is once again familiarizing herself with the top spot of the Hot 100! Set Fire to the Rain spent its second week at number one but Kelly Clarkson's..."),
            new Link(new BigInteger("27"),
                    "http://maltansas.com/8912395",
                    "http://maltansas-3907744.img.mlv-cdn.com/img/fe6a624c054b0744fac386288541c325",
                    "18 Celebrity Pairs You'll Be Shocked Are The Same Age",
                    "Kate Upton and Selena Gomez, 1992 | From maltansas"),
            new Link(new BigInteger("28"),
                    "http://p665d.tk/",
                    "http://i.perezhilton.com/wp-content/uploads/2011/08/rihanna-denies-sex-tape__oPt.jpg",
                    "Rihanna Denies Sex Tape!",
                    "And here comes the denial! Despite reports surfacing yesterday that Hustler had acquired a seksi no-no times tape featuring J. Cole and Rihanna, the singer..."),
            new Link(new BigInteger("29"),
                    "http://www.vanityfair.com/online/daily/2014/07/monica-lewinsky-orange-is-the-new-black-online-rebuttals",
                    "http://photos.vanityfair.com/2015/01/29/54cab7f4b624d69105764efc_image.jpg",
                    "Monica Lewinsky: The Online Rebuttal Is the New Black",
                    "Monica Lewinsky explores the phenomenon of the online rebuttal, and how the new course of action gives those who feel wronged a stronger voice."),
            new Link(new BigInteger("30"),
                    "http://www.bbc.com/news/world-europe-32856232",
                    "http://ichef.bbci.co.uk/news/1024/media/images/83184000/jpg/_83184928_uqla1i0m.jpg",
                    "Ireland same-sex referendum set to approve gay marriage - BBC News",
                    "Early indications suggest the Republic of Ireland has voted to legalise same-sex marriage in a historic referendum."),
            new Link(new BigInteger("31"),
                    "http://ipptm.tk/",
                    "http://i.perezhilton.com/wp-content/uploads/2012/03/the-big-three-overexposed-real__oPt.jpg",
                    "Kim Kardashian, Lindsay Lohan And Snooki Expose Themselves!!!",
                    "Expose themselves too much in the public eye, that is. Forbes took a poll and has released a list of the most overexposed celebrities. Kim Kardashian comes..."),
            new Link(new BigInteger("32"),
                    "http://www.businessweek.com/articles/2014-10-13/how-kims-half-sister-kylie-became-the-most-influential-kardashian",
                    "http://media.gotraffic.net/images/iJTbdpRiTU14/v10/-1x-1.jpg",
                    "How Kim’s Half-Sister Kylie Became the Most Influential Kardashian",
                    "The Kardashian sisters have been asking 17-year-old Kylie Jenner for fashion advice for at least two or three years"),
            new Link(new BigInteger("33"),
                    "http://analytical-solution.com/2015/02/09/honored-to-be-a-women-in-data/",
                    "Data Science is real » Honored to be a Women In Data",
                    null,
                    null),
            new Link(new BigInteger("34"),
                    "http://www.bbc.com/news/world-us-canada-32856926",
                    "http://ichef.bbci.co.uk/news/1024/media/images/83183000/jpg/_83183617_fe5168e7-d14e-416e-9bea-255cf17dc3b6.jpg",
                    "UN nuclear weapons talks fail 'over Israel row' - BBC News",
                    "A UN conference aimed at preventing the proliferation of nuclear weapons ends in failure after a row over a nuclear-free Middle East proposal."),
            new Link(new BigInteger("35"),
                    "http://www.vox.com/2015/5/16/8613019/cuba-lung-cancer-vaccine",
                    "https://cdn1.vox-cdn.com/thumbor/SCKptHXuYy0MwkK37hic1PwXTZY=/0x204:700x593/1080x600/cdn0.vox-cdn.com/uploads/chorus_image/image/46342358/459819113.0.0.0.0.jpg",
                    "Cuba has a possible lung cancer vaccine that America can now test",
                    "Medical innovation may be the most surprising benefit of the thawing of diplomatic relations between the two countries."),
            new Link(new BigInteger("36"),
                    "http://www.bbc.com/news/education-32849563",
                    "http://ichef.bbci.co.uk/news/1024/media/images/83178000/jpg/_83178450_anonymouschildren.jpg",
                    "Merge adoption services to end delays, councils told - BBC News",
                    "Councils in England could be forced to merge their services to speed up adoption rates, under new government powers to be announced next week."),
            new Link(new BigInteger("37"),
                    "http://www.vanityfair.com/style/the-international-best-dressed-list",
                    "http://photos.vanityfair.com/2014/08/01/53dbe4474dddd5cd7d3cd9a7_s-bdl-2014.jpg",
                    "The 2014 International Best-Dressed List",
                    null),
            new Link(new BigInteger("38"),
                    "http://mobi.perezhilton.com/2012-03-22-lindsay-lohan-accuser-may-be-committing-insurance-fraud?utm_source=twitterfeed&utm_medium=twitter#.VWGM4C9dbCQ",
                    "http://i.perezhilton.com/wp-content/uploads/2012/03/lilo-mone__oPt.jpg",
                    "Lindsay Lohan's Hit-And-Run Accuser May Be Wanted For All Sorts Of Insurance Fraud",
                    "We have to admit, this hit-and-run nonsense is looking less and less like Lindsay Lohan's fault and more and more like someone is trying to take advantage of..."),
            new Link(new BigInteger("39"),
                    "http://zte3d.tk/",
                    "http://i.perezhilton.com/wp-content/uploads/2012/10/nicki-minaj-stevie-nicks-wenn-ivan-nikolov-michael-carpenter__oPt.jpg",
                    "Nicki Minaj Making Up With Stevie Nicks After Ill-Conceived Murder Threat?",
                    "Stevie Nicks feels terrible about her off the cuff quasi-threat toward Nicki Minaj yesterday! She was just rushing to the defense of her home-girl Mariah Carey but she knows it's no excuse for joking about murdering someone! In a candid statement, the Fleetwood Mac master songstress said:"),
            new Link(new BigInteger("40"),
                    "http://www.bbc.com/news/world-europe-32764373",
                    "http://ichef.bbci.co.uk/news/1024/media/images/83041000/jpg/_83041887_027234486.jpg",
                    "German train crash kills two near Ibbenbueren - BBC News",
                    "At least two people have been killed and 20 injured in western Germany after a train collided with a tractor-trailer on a level crossing, police say."),
            new Link(new BigInteger("41"),
                    "http://www.eonline.com/news/657085/former-spice-girls-geri-halliwell-is-married-see-her-stunning-wedding-dress-and-all-the-famous-guests",
                    "http://www.eonline.com/resize/500/500//eol_images/Entire_Site/2015415/rs_300x300-150515085940-600-geri-halliwell-married-wedding.ls.51515.jpg",
                    "Spice Girls Singer Geri Halliwell Is Married!",
                    "Ginger Spice is hitched! Former Spice Girlsstar Geri Halliwell has tied the knot with her longtim..."),
            new Link(new BigInteger("42"),
                    "http://news.yahoo.com/earthquakes-hawaii-volcano-could-signal-eruption-002845230.html",
                    "http://l1.yimg.com/bt/api/res/1.2/evNm5k3b_gktB6QUIP95Ug--/YXBwaWQ9eW5ld3M7aWw9cGxhbmU7cT03NTt3PTYwMA--/http://media.zenfs.com/en_us/News/ap_webfeeds/fa7b2b190c87b116760f6a7067007c41.jpg",
                    "Earthquakes on Hawaii volcano could signal new eruption",
                    "HONOLULU (AP) — A series of earthquakes and shifting ground on the slopes of Kilauea have scientists wondering what will happen next at one of the world's most active volcanos."),
            new Link(new BigInteger("43"),
                    "http://www.startrekmovie.com/",
                    "http://www.startrekmovie.com/images/fb_og_img.jpg",
                    "Star Trek Into Darkness | Trailer & Official Movie Site | ",
                    "Star Trek Into Darkness - Own it TODAY on Blu-ray, DVD, and Digital"),
            new Link(new BigInteger("44"),
                    "http://www.transcendancing.net/tag/indie/",
                    "http://www.transcendancing.net/wp-content/uploads/2015/01/Russ-How-to-Suppress-Women-graphic-194x300.jpg",
                    "indie | The Conversationalist",
                    "In an effort to shut up and write about stuff rather than wait for it to be ‘worth talking about’, I thought I’d do some posts about movies, ones I’ve seen and want to see. The list of movies I still "),
            new Link(new BigInteger("45"),
                    "http://www.washingtonpost.com/world/africa/suicide-bomb-attack-kills-8-at-bus-station-in-northeastern-nigeria/2015/05/16/0f5b1e7a-fc01-11e4-9ef4-1bb7ce3b3fb7_story.html",
                    "http://img.washingtonpost.com/pb/resources/img/twp-2048x1024.jpg",
                    "Suicide bomb attack kills 8 at bus station in northeastern Nigeria",
                    "More than 30 others were injured in the blast that is suspected to have been orchestrated by Boko Haram."),
            new Link(new BigInteger("46"),
                    "http://abcnews.go.com/International/wireStory/afghan-official-suicide-bomber-strikes-kabul-airport-31100578",
                    "http://a.abcnews.com/assets/images/v3/pixel_eee.gif",
                    "Suicide Car Bomb Near Kabul Airport Kills 3",
                    "A Taliban suicide bomber detonated an explosives-packed car near the international airport in Afghanistan's capital on Sunday, killing at least three people and wounding 18 in an attack that appears to have targeted vehicles of the European Union police training mission, officials said. A..."),
            new Link(new BigInteger("47"),
                    "http://fantasticfourmovieonline.com/",
                    "http://i.imgur.com/yghKp9r.jpg",
                    "Fantastic Four Full Movie Online",
                    "Watch Fantastic Four Full Movie Online for free"),
            new Link(new BigInteger("48"),
                    "http://omborokko.com/bathroom-ornamentation-with-elegance-by-fashionable-fresca-range/",
                    "http://omborokko.com/wp-content/uploads/2015/05/Amazing-twin-white-porcelain-sink-mixing-with-stainless-faucet-looks-nice-put-above-cabinet-with-many-drawer-storage.jpg",
                    "Bathroom: Bathroom Ornamentation With Elegance By Fashionable Fresca Range, bedroom arrangement ideas with 2 beds, bedroom arrangement ideas for small rooms’ ~ Omborokko.com",
                    "Home » Bathroom » Bathroom Ornamentation with Elegance by Fashionable Fresca Range Posted by Carina Aquilla, Bathroom. May 26th, 2015 For plenty of us a bathroom is a preferential places where do more"),
            new Link(new BigInteger("49"),
                    "http://www.teainthetreetops.com/2014/06/podcast-episode-8-movies/",
                    "http://www.teainthetreetops.com/wp-content/uploads/2014/04/teatinthetreetops_podcastlogo.png",
                    "Podcast Episode #8: At the Movies - Tea in the Treetops",
                    "Episode eight of Tea in the Treetops podcast focusing on YA books, book events and Book to Movie Adaptations"),
            new Link(new BigInteger("50"),
                    "http://armavir.yuginform.ru/catalog/searchresults?typeOfSearch=all&what=%D1%88%D0%BA%D0%B0%D1%84%D1%8B",
                    "http://armavir.yuginform.ru/media/ban/1425024607.jpg",
                    "Шкафы — Армавир",
                    "Шкафы — Армавир Если вас не устраивают результаты поиска, попробуйте изменить запрос, убрать неключевые слова из поискового запроса и/или оптимизируйте вид поиска. Первое слово в запросе самое важное "
            ));

    private final Map<BigInteger, List<Link>> rooms = new ConcurrentHashMap<>();
    private final Map<BigInteger, String> roomNamesById = new ConcurrentHashMap<>();
    private final Map<String, BigInteger> roomIdsByName = new ConcurrentHashMap<>();

    private final AtomicLong counter = new AtomicLong(1L);

    @Autowired
    private ContentRoomRepository contentRoomRepository;

    public ContentRoomServiceImpl() {
        rooms.put(BigInteger.ONE, new CopyOnWriteArrayList<>(LINKS));
        roomIdsByName.put("default", BigInteger.ONE);
        roomNamesById.put(BigInteger.ONE, "default");
    }

    @Override
    public ContentRoom createRoom(final ContentRoom room) {
        final String name = room.getName();
        if (roomIdsByName.containsKey(name)) {
            throw new ObjectAlreadyExistsException("Content room: " + name);
        }
        final BigInteger id = BigInteger.valueOf(counter.incrementAndGet());
        roomIdsByName.put(name, id);
        roomNamesById.put(id, name);
        rooms.put(id, new CopyOnWriteArrayList<>());

        final ContentRoom savedRoom = contentRoomRepository.save(room);

        return new ContentRoom(id, name, null);
    }

    @Override
    public ContentRoom deleteRoom(final ContentRoom room) {
        final String name = room.getName();
        final BigInteger id = roomIdsByName.remove(name);
        if (id != null) {
            roomIdsByName.remove(name);
            roomNamesById.remove(id);
            rooms.remove(id);
        }

        return new ContentRoom(id, name, null);
    }

    @Override
    public List<ContentRoom> getRooms() {
        return roomIdsByName.entrySet().stream().map(e -> new ContentRoom(e.getValue(), e.getKey(), null)).collect(Collectors.toList());
    }

    @Override
    public ContentRoom findById(final BigInteger id) {
        final String name = roomNamesById.get(id);
        if (name == null) {
            throw new ObjectNotFoundException("Content room :" + id.toString());
        }

        return new ContentRoom(id, name, null);
    }

    @Override
    public Link addLinkToRoom(final BigInteger roomId, final Link link) {
        final String name = roomNamesById.get(roomId);
        if (name == null) {
            throw new ObjectNotFoundException("Content room: " + roomId.toString());
        }

        List<Link> links = rooms.get(roomId);
        if (links == null) {
            links = new CopyOnWriteArrayList<>();
            rooms.put(roomId, links);
        }
        links.add(link);

        return link;
    }

    @Override
    public Link removeLinkFromRoom(final BigInteger roomId, final Link link) {
        final String name = roomNamesById.get(roomId);
        if (name == null) {
            throw new ObjectNotFoundException("Content room: " + roomId.toString());
        }

        final List<Link> links = rooms.get(roomId);
        if (links != null) {
            links.remove(link);
        }

        return link;
    }

    @Override
    public List<Link> findLinks(final BigInteger roomId, final long startIndex, final long pageSize) {
        final List<Link> result;

        final List<Link> links = rooms.get(roomId);
        if (links != null && startIndex >= 0 && startIndex < links.size()) {
            final int fromIndex = (int) startIndex;
            final int toIndex = Math.min((int) (startIndex + pageSize), links.size());
            result = links.subList(fromIndex, toIndex);
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

    @Override
    public Link findLinkById(final BigInteger id) {
        Link result = null;

        final Iterator<List<Link>> mapValueIterator = rooms.values().iterator();
        while (mapValueIterator.hasNext() && result == null) {
            final List<Link> links = mapValueIterator.next();
            final Iterator<Link> linkIterator = links.iterator();
            while (linkIterator.hasNext() && result == null) {
                final Link link = linkIterator.next();
                if (id.equals(link.getId())) {
                    result = link;
                }
            }
        }

        if (result == null) {
            throw new ObjectNotFoundException("Link: " + id.toString());
        }

        return result;
    }
}
