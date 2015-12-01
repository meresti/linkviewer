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

export class LinkService {
    links = [
        {
            "url": "http://www.nevillehobson.com/2014/09/09/transparent-wearable-technology-within-enterprise/",
            "imageUrl": "http://www.nevillehobson.com/wp-content/uploads/closeup1-3201.jpg",
            "title": "How transparent is wearable technology within the enterprise? - NevilleHobson.com",
            "description": "In July, I took part in a public debate at the House of Commons about ethics in PR and wearable technology. Organized by The Debating Group and sponsored by the CIPR, the debate served a highly useful purpose of bringing a timely topic to front of mind amongst a community of communicators which considered the …",
            links: [
                {rel: "removeFromRoom", href: "http://localhost:9000/app/links/remove"},
                {rel: "like", href: "http://localhost:9000/app/links/like"},
                {rel: "dislike", href: "http://localhost:9000/app/links/dislike"},
                {rel: "bufferShare", href: "http://localhost:9000/app/links/bufferShare"},
                {rel: "pocketShare", href: "http://localhost:9000/app/links/pocketShare"},
                {rel: "favoritize", href: "http://localhost:9000/app/links/favoritize"}
            ]
        },
        {
            "url": "http://en.wikipedia.org/wiki/Wearable_technology",
            "imageUrl": "http://bits.wikimedia.org/images/wikimedia-button.png",
            "title": "Wearable technology - Wikipedia, the free encyclopedia",
            "description": "Wearable technology, wearables, fashionable technology, wearable devices, tech togs, or fashion electronics are clothing and accessories incorporating computer and advanced electronic technologies. Th"
        },
        {
            "url": "http://www.gizmodo.com.au/2015/01/these-augmented-reality-glasses-are-james-bond-worthy/",
            "imageUrl": "http://i.kinja-img.com/gawker-media/image/upload/t_xlarge/f5qeo8wlj4qtpeu4wppd.jpg",
            "title": "These Augmented Reality Glasses Are James Bond-Worthy",
            "description": "Ever heard of Ralph Osterhout? He’s known as the real-life “Q”. He created underwater vehicles featured in two James Bond movies. He..."
        },
        {
            "url": "http://www.socialfresh.com/content-curation-scott-monty/",
            "imageUrl": "http://www.socialfresh.com/content/uploads/2015/02/5-content-curation-secrets-from-scott-monty-134x134.png",
            "title": "5 Content Curation Secrets From Scott Monty",
            "description": "Content curation is a game of numbers and filtering. To do it well, to be a depended upon news source for an audience, it also takes an attention to detail and consistency. Making sure the content you find and share is..."
        },
        {
            "url": "http://www.rottentomatoes.com/m/the_amazing_spider_man_2/",
            "imageUrl": "http://resizing.flixster.com/8o3Z57xCEagHAkwEGRvhzyMFrH0=/800x1200/dkpu1ddg7pbsk.cloudfront.net/movie/11/18/00/11180032_ori.jpg",
            "title": "The Amazing Spider-Man 2",
            "description": "Critics Consensus: While the cast is outstanding and the special effects are top-notch, the latest installment of the Spidey saga suffers from an unfocused narrative and an overabundance of characters."
        },
        {
            "url": "http://www.rottentomatoes.com/m/captain_america_the_winter_soldier_2014/",
            "imageUrl": "http://resizing.flixster.com/LBvcxGaeGVRboDUr1PHf9vMNe70=/405x600/dkpu1ddg7pbsk.cloudfront.net/movie/11/17/72/11177246_ori.jpg",
            "title": "Captain America: The Winter Soldier",
            "description": "Critics Consensus: Suspenseful and politically astute, Captain America: The Winter Soldier is a superior entry in the Avengers canon and is sure to thrill Marvel diehards."
        },
        {
            "url": "http://m.huffpost.com/us/entry/7264904",
            "imageUrl": "http://i.huffpost.com/gen/1464944/images/o-US_UK_CA-facebook.jpg",
            "title": "Your Clothes Are Still Made In China But Now They Are Wearables",
            "description": "Just like any other trip, I fretted a bit over what to pack, what to wear? After all, Hong Kong is a fashion mecca, shopping destination and everyone is wearing the latest trends. What was everyone wearing this season in Hong Kong? Wearable technology, that's what."
        },
        {
            "url": "http://www.scoop.it/t/data-nerd-s-corner/p/4028567073/2014/09/23/the-platform-problem-why-big-data-outcomes-still-aren-t-real-time",
            "imageUrl": "http://img.scoop.it/1ApTH5As4WMh4VFAVAq2moXXXL4j3HpexhjNOf_P3Yk8eJwghM0vysm9vt0-fRFi",
            "title": "The Platform Problem: Why Big Data Outcomes Sti...",
            "description": "And when these big data platforms perfectly connect these dots, we’ll see the surge in need for data scientists and chief data officers dwindle. Instead, these data-minded folks will find work within the companies building these platforms and technologies themselves. After all, that is truly wher..."
        },
        {
            "url": "http://www.scoop.it/t/data-nerd-s-corner/p/4028545623/2014/09/23/rogue-big-data-projects-running-wild-across-businesses",
            "imageUrl": "http://img.scoop.it/pB_psTu-PfmYg7peIyYsIoXXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
            "title": "Rogue big data projects running wild across bus...",
            "description": "Dorney said it was a misconception to think big data analysis is for the big end of town as all types of organisations can take advantage. “Storing and accessing data is available to all. Making a start with data collection is what counts, even if you’re not quite sure why and how you’ll use t..."
        },
        {
            "url": "http://www.businessinsider.com/i-was-assaulted-for-wearing-google-glass-2014-4",
            "imageUrl": "http://static1.businessinsider.com/image/5349ab1b6da811b458a1d22e/i-was-assaulted-for-wearing-google-glass-in-the-wrong-part-of-san-francisco.jpg",
            "title": "I Was Assaulted For Wearing Google Glass In The Wrong Part Of San Francisco",
            "description": "It happened in an instant."
        },
        {
            "url": "http://sco.lt/5zaxv7",
            "imageUrl": "http://img.scoop.it/2oV5EsP4AvFcAStt3d6qhIXXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
            "title": "Real-Time Revenue and Big Data | SmartData Coll...",
            "description": "Big Data Pitfalls For those ready to dive into a Big Data implementation, be sure to weigh the pros and cons. According to a recent report by research firm Frost & Sullivan, three of the most common problems in big data deployments are incomplete data collection, false starts, and disruptive dra..."
        },
        {
            "url": "http://patriot-tech.com/wearable-technology-trends-2014/",
            "imageUrl": "http://patriot-tech.com/wp-content/uploads/2014/06/wearable-technology-man-300x248.jpg",
            "title": "Wearable Technology Trends 2014 - Where to Go With Wearables",
            "description": "Wearable technology 2014 - It's almost impossible to pick up a magazine without running into the latest tech trend - wearables. See what the buzz is about"
        },
        {
            "url": "http://renanablog.com/tag/google-glass/",
            "imageUrl": "http://d13pix9kaak6wt.cloudfront.net/avatar/users/r/a/p/raphaelfreeman_1395225982_42.png",
            "title": "google glass | The Renana Blog",
            "description": "Main menu When my father passed away recently I inherited his gold watch. Like most watches, it tells the time. The design is timeless and despite the fact that my father received this for his wedding"
        },
        {
            "url": "http://www.tnooz.com/article/big-data-not-messiah/",
            "imageUrl": "http://www.tnooz.com/wp-content/uploads/2014/10/messiah-technology.jpg",
            "title": "Big Data is not the Messiah coming to save travel personalisation",
            "description": "Over the last year I’ve exposed myself to a lot of Big Data blogs, articles, conferences, webinars, technologies, chats over beers, and industry investments."
        },
        {
            "url": "http://fredlybrand.com/2014/04/15/wearable-technology-and-the-state-of-the-art-in-fabrics/",
            "imageUrl": "http://fredlybranddotcom.files.wordpress.com/2014/04/1-fullscreen-capture-4122014-60152-am.jpg?w=300",
            "title": "Wearable Technology and the State of the Art in Fabrics",
            "description": "Wearable technology, as defined by Wikipedia to the right, integrates electronics into a wearable item, often into conventional apparel. There is often an electronic device, housed in hard plastic ..."
        },
        {
            "url": "http://www.iflscience.com/technology/new-type-computer-capable-calculating-640tbs-data-one-billionth-second-could",
            "imageUrl": "http://www.iflscience.com/sites/www.iflscience.com/files/blog/%5Bnid%5D/HP_the-machine.jpg",
            "title": "New Type Of Computer Capable Of Calculating 640TBs Of Data In One Billionth Of A Second, Could Revolutionize Computing",
            "description": "Let me introduce The Machine- HP’s latest invention that could revolutionize the computing world. According to HP, The Machine is not a server, workstation, PC, device or phone but an amalgamation of all these things."
        },
        {
            "url": "http://fortune.com/2014/06/13/these-big-data-companies-are-ones-to-watch/",
            "imageUrl": "https://fortunedotcom.files.wordpress.com/2014/06/104821290.jpg?quality=80&w=820&h=570&crop=1",
            "title": "These big data companies are ones to watch",
            "description": "Which companies are breaking new ground with big data technology? We ask 10 industry experts."
        },
        {
            "url": "http://www.scoop.it/t/data-nerd-s-corner/p/4028192211/2014/09/17/top-cities-and-other-demographics-for-data-scientists",
            "imageUrl": "http://img.scoop.it/LGgZBIqib2AlhMbUiqb9P4XXXL4j3HpexhjNOf_P3YmryPKwJ94QGRtDb3Sbc6KY",
            "title": "Top Cities and Other Demographics for Data Scie...",
            "description": "This analysis shows the distribution of data scientists per country, city, gender and company. It is based on Data Science Central (DSC) member database, only…"
        },
        {
            "url": "http://www.forbes.com/fdc/welcome_mjx_mobile.html",
            "title": "Welcome to Forbes",
            "description": "Forbes Continue » Thought Of The Day ADVERTISEMENT "
        },
        {
            "url": "http://www.slate.com/blogs/xx_factor/2014/09/02/jennifer_lawrence_and_other_celebrity_hacking_victims_should_not_have_to.html",
            "imageUrl": "http://www.slate.com/content/dam/slate/blogs/xx_factor/2014/09/02/jennifer_lawrence_and_other_celebrity_hacking_victims_should_not_have_to/162636595-jennifer-lawrence-carrying-her-oscar-for-best-actress.jpg/_jcr_content/renditions/cq5dam.web.1280.1280.jpeg",
            "title": "“Don't Take Nude Selfies,” Shrug It Off, and Other Gross Advice for Hacked Celebs",
            "description": "On Sunday, dozens of nude photographs of female celebrities—including Jennifer Lawrence, Kate Upton, and Kirsten Dunst—were leaked online. The hacking and nonconsensual publication of these photos—which were kept privately by the women themselves and shared only with partners and friends of their choosing, if with anyone at all—is both a..."
        },
        {
            "url": "http://www.vanityfair.com/style/scandal/2014/09/Monica-Lewinsky-photo-hacking-scandal",
            "imageUrl": "http://photos.vanityfair.com/2015/01/29/54cac41cb8f23e3a0315af35_image.png",
            "title": "Nude Traffic: When Have We Crossed the Double Yellow Line?",
            "description": "For all of our Instagram-enabled narcissism these days, there is no small degree of assault involved in having our private thoughts, conversations, and photos dished up for the entertainment of the masses."
        },
        {
            "url": "http://www.dailymail.co.uk/tvshowbiz/article-3084093/I-don-t-need-little-boy-Paris-Hilton-strips-sexy-lingerie-steamy-new-music-video-High-Love.html",
            "imageUrl": "http://i.dailymail.co.uk/i/pix/2015/05/16/04/28BBF01700000578-0-image-a-75_1431746080972.jpg",
            "title": "Paris Hilton cavorts in lingerie in new music video High Off My Love",
            "description": "The 34-year-old heiress strips down to her lingerie to show off her taut physique in the sexy video, where she breathily sings, 'I don't need a little boy'."
        },
        {
            "url": "http://www.theguardian.com/music/2015/may/18/taylor-swift-winner-calvin-harris-2015-billboard-music-awards",
            "imageUrl": "http://static.guim.co.uk/sys-images/Guardian/Pix/pictures/2015/5/18/1431933821856/d738a38a-97fa-4739-a914-cbdd84c8c75c-2060x1236.jpeg",
            "title": "Taylor Swift is big winner at 2015 Billboard music awards",
            "description": "The pop star swept the board at this year’s ceremony in Las Vegas, taking home a total of eight prizes"
        },
        {
            "url": "http://www.bbc.com/news/world-africa-32765128",
            "imageUrl": "http://ichef.bbci.co.uk/news/1024/media/images/83041000/jpg/_83041947_027231635.jpg",
            "title": "Burundi crackdown after failed coup against Nkurunziza - BBC News",
            "description": "Eighteen people appear in court in Burundi as the government begins a crackdown against those suspected of involvement in a failed coup."
        },
        {
            "url": "http://rt.com/news/261385-us-hunts-russian-citizens/",
            "imageUrl": "http://rt.com/files/news/3f/d0/90/00/us-hunts-russian-citizens.jpg",
            "title": "‘US hunts for Russians’: Moscow warns citizens traveling abroad",
            "description": "Russia has urged its citizens to weigh all the risks before traveling abroad, warning that the US is on a global “hunt” for Russian nationals, according to the latest statement published by the Foreign Ministry."
        },
        {
            "url": "http://yisbe.tk/",
            "imageUrl": "http://i.perezhilton.com/wp-content/uploads/2012/02/adele-spends-second-week-on-top-of-billboard-web__oPt.jpg",
            "title": "Adele Sets Fire To The Billboard Charts For The Second Week In A Row",
            "description": "Adele is once again familiarizing herself with the top spot of the Hot 100! Set Fire to the Rain spent its second week at number one but Kelly Clarkson's..."
        },
        {
            "url": "http://maltansas.com/8912395",
            "imageUrl": "http://maltansas-3907744.img.mlv-cdn.com/img/fe6a624c054b0744fac386288541c325",
            "title": "18 Celebrity Pairs You'll Be Shocked Are The Same Age",
            "description": "Kate Upton and Selena Gomez, 1992 | From maltansas"
        },
        {
            "url": "http://p665d.tk/",
            "imageUrl": "http://i.perezhilton.com/wp-content/uploads/2011/08/rihanna-denies-sex-tape__oPt.jpg",
            "title": "Rihanna Denies Sex Tape!",
            "description": "And here comes the denial! Despite reports surfacing yesterday that Hustler had acquired a seksi no-no times tape featuring J. Cole and Rihanna, the singer..."
        },
        {
            "url": "http://www.vanityfair.com/online/daily/2014/07/monica-lewinsky-orange-is-the-new-black-online-rebuttals",
            "imageUrl": "http://photos.vanityfair.com/2015/01/29/54cab7f4b624d69105764efc_image.jpg",
            "title": "Monica Lewinsky: The Online Rebuttal Is the New Black",
            "description": "Monica Lewinsky explores the phenomenon of the online rebuttal, and how the new course of action gives those who feel wronged a stronger voice."
        },
        {
            "url": "http://www.bbc.com/news/world-europe-32856232",
            "imageUrl": "http://ichef.bbci.co.uk/news/1024/media/images/83184000/jpg/_83184928_uqla1i0m.jpg",
            "title": "Ireland same-sex referendum set to approve gay marriage - BBC News",
            "description": "Early indications suggest the Republic of Ireland has voted to legalise same-sex marriage in a historic referendum."
        },
        {
            "url": "http://ipptm.tk/",
            "imageUrl": "http://i.perezhilton.com/wp-content/uploads/2012/03/the-big-three-overexposed-real__oPt.jpg",
            "title": "Kim Kardashian, Lindsay Lohan And Snooki Expose Themselves!!!",
            "description": "Expose themselves too much in the public eye, that is. Forbes took a poll and has released a list of the most overexposed celebrities. Kim Kardashian comes..."
        },
        {
            "url": "http://www.businessweek.com/articles/2014-10-13/how-kims-half-sister-kylie-became-the-most-influential-kardashian",
            "imageUrl": "http://media.gotraffic.net/images/iJTbdpRiTU14/v10/-1x-1.jpg",
            "title": "How Kim’s Half-Sister Kylie Became the Most Influential Kardashian",
            "description": "The Kardashian sisters have been asking 17-year-old Kylie Jenner for fashion advice for at least two or three years"
        },
        {
            "url": "http://analytical-solution.com/2015/02/09/honored-to-be-a-women-in-data/",
            "title": "Data Science is real » Honored to be a Women In Data"
        },
        {
            "url": "http://www.bbc.com/news/world-us-canada-32856926",
            "imageUrl": "http://ichef.bbci.co.uk/news/1024/media/images/83183000/jpg/_83183617_fe5168e7-d14e-416e-9bea-255cf17dc3b6.jpg",
            "title": "UN nuclear weapons talks fail 'over Israel row' - BBC News",
            "description": "A UN conference aimed at preventing the proliferation of nuclear weapons ends in failure after a row over a nuclear-free Middle East proposal."
        },
        {
            "url": "http://www.vox.com/2015/5/16/8613019/cuba-lung-cancer-vaccine",
            "imageUrl": "https://cdn1.vox-cdn.com/thumbor/SCKptHXuYy0MwkK37hic1PwXTZY=/0x204:700x593/1080x600/cdn0.vox-cdn.com/uploads/chorus_image/image/46342358/459819113.0.0.0.0.jpg",
            "title": "Cuba has a possible lung cancer vaccine that America can now test",
            "description": "Medical innovation may be the most surprising benefit of the thawing of diplomatic relations between the two countries."
        },
        {
            "url": "http://www.bbc.com/news/education-32849563",
            "imageUrl": "http://ichef.bbci.co.uk/news/1024/media/images/83178000/jpg/_83178450_anonymouschildren.jpg",
            "title": "Merge adoption services to end delays, councils told - BBC News",
            "description": "Councils in England could be forced to merge their services to speed up adoption rates, under new government powers to be announced next week."
        },
        {
            "url": "http://www.vanityfair.com/style/the-international-best-dressed-list",
            "imageUrl": "http://photos.vanityfair.com/2014/08/01/53dbe4474dddd5cd7d3cd9a7_s-bdl-2014.jpg",
            "title": "The 2014 International Best-Dressed List"
        },
        {
            "url": "http://mobi.perezhilton.com/2012-03-22-lindsay-lohan-accuser-may-be-committing-insurance-fraud?utm_source=twitterfeed&utm_medium=twitter#.VWGM4C9dbCQ",
            "imageUrl": "http://i.perezhilton.com/wp-content/uploads/2012/03/lilo-mone__oPt.jpg",
            "title": "Lindsay Lohan's Hit-And-Run Accuser May Be Wanted For All Sorts Of Insurance Fraud",
            "description": "We have to admit, this hit-and-run nonsense is looking less and less like Lindsay Lohan's fault and more and more like someone is trying to take advantage of..."
        },
        {
            "url": "http://zte3d.tk/",
            "imageUrl": "http://i.perezhilton.com/wp-content/uploads/2012/10/nicki-minaj-stevie-nicks-wenn-ivan-nikolov-michael-carpenter__oPt.jpg",
            "title": "Nicki Minaj Making Up With Stevie Nicks After Ill-Conceived Murder Threat?",
            "description": "Stevie Nicks feels terrible about her off the cuff quasi-threat toward Nicki Minaj yesterday! She was just rushing to the defense of her home-girl Mariah Carey but she knows it's no excuse for joking about murdering someone! In a candid statement, the Fleetwood Mac master songstress said:"
        },
        {
            "url": "http://www.bbc.com/news/world-europe-32764373",
            "imageUrl": "http://ichef.bbci.co.uk/news/1024/media/images/83041000/jpg/_83041887_027234486.jpg",
            "title": "German train crash kills two near Ibbenbueren - BBC News",
            "description": "At least two people have been killed and 20 injured in western Germany after a train collided with a tractor-trailer on a level crossing, police say."
        },
        {
            "url": "http://www.eonline.com/news/657085/former-spice-girls-geri-halliwell-is-married-see-her-stunning-wedding-dress-and-all-the-famous-guests",
            "imageUrl": "http://www.eonline.com/resize/500/500//eol_images/Entire_Site/2015415/rs_300x300-150515085940-600-geri-halliwell-married-wedding.ls.51515.jpg",
            "title": "Spice Girls Singer Geri Halliwell Is Married!",
            "description": "Ginger Spice is hitched! Former Spice Girlsstar Geri Halliwell has tied the knot with her longtim..."
        },
        {
            "url": "http://news.yahoo.com/earthquakes-hawaii-volcano-could-signal-eruption-002845230.html",
            "imageUrl": "http://l1.yimg.com/bt/api/res/1.2/evNm5k3b_gktB6QUIP95Ug--/YXBwaWQ9eW5ld3M7aWw9cGxhbmU7cT03NTt3PTYwMA--/http://media.zenfs.com/en_us/News/ap_webfeeds/fa7b2b190c87b116760f6a7067007c41.jpg",
            "title": "Earthquakes on Hawaii volcano could signal new eruption",
            "description": "HONOLULU (AP) — A series of earthquakes and shifting ground on the slopes of Kilauea have scientists wondering what will happen next at one of the world's most active volcanos."
        },
        {
            "url": "http://www.startrekmovie.com/",
            "imageUrl": "http://www.startrekmovie.com/images/fb_og_img.jpg",
            "title": "Star Trek Into Darkness | Trailer & Official Movie Site | ",
            "description": "Star Trek Into Darkness - Own it TODAY on Blu-ray, DVD, and Digital"
        },
        {
            "url": "http://www.transcendancing.net/tag/indie/",
            "imageUrl": "http://www.transcendancing.net/wp-content/uploads/2015/01/Russ-How-to-Suppress-Women-graphic-194x300.jpg",
            "title": "indie | The Conversationalist",
            "description": "In an effort to shut up and write about stuff rather than wait for it to be ‘worth talking about’, I thought I’d do some posts about movies, ones I’ve seen and want to see. The list of movies I still "
        },
        {
            "url": "http://www.washingtonpost.com/world/africa/suicide-bomb-attack-kills-8-at-bus-station-in-northeastern-nigeria/2015/05/16/0f5b1e7a-fc01-11e4-9ef4-1bb7ce3b3fb7_story.html",
            "imageUrl": "http://img.washingtonpost.com/pb/resources/img/twp-2048x1024.jpg",
            "title": "Suicide bomb attack kills 8 at bus station in northeastern Nigeria",
            "description": "More than 30 others were injured in the blast that is suspected to have been orchestrated by Boko Haram."
        },
        {
            "url": "http://abcnews.go.com/International/wireStory/afghan-official-suicide-bomber-strikes-kabul-airport-31100578",
            "imageUrl": "http://a.abcnews.com/assets/images/v3/pixel_eee.gif",
            "title": "Suicide Car Bomb Near Kabul Airport Kills 3",
            "description": "A Taliban suicide bomber detonated an explosives-packed car near the international airport in Afghanistan's capital on Sunday, killing at least three people and wounding 18 in an attack that appears to have targeted vehicles of the European Union police training mission, officials said. A..."
        },
        {
            "url": "http://fantasticfourmovieonline.com/",
            "imageUrl": "http://i.imgur.com/yghKp9r.jpg",
            "title": "Fantastic Four Full Movie Online",
            "description": "Watch Fantastic Four Full Movie Online for free"
        },
        {
            "url": "http://omborokko.com/bathroom-ornamentation-with-elegance-by-fashionable-fresca-range/",
            "imageUrl": "http://omborokko.com/wp-content/uploads/2015/05/Amazing-twin-white-porcelain-sink-mixing-with-stainless-faucet-looks-nice-put-above-cabinet-with-many-drawer-storage.jpg",
            "title": "Bathroom: Bathroom Ornamentation With Elegance By Fashionable Fresca Range, bedroom arrangement ideas with 2 beds, bedroom arrangement ideas for small rooms’ ~ Omborokko.com",
            "description": "Home » Bathroom » Bathroom Ornamentation with Elegance by Fashionable Fresca Range Posted by Carina Aquilla, Bathroom. May 26th, 2015 For plenty of us a bathroom is a preferential places where do more"
        },
        {
            "url": "http://www.teainthetreetops.com/2014/06/podcast-episode-8-movies/",
            "imageUrl": "http://www.teainthetreetops.com/wp-content/uploads/2014/04/teatinthetreetops_podcastlogo.png",
            "title": "Podcast Episode #8: At the Movies - Tea in the Treetops",
            "description": "Episode eight of Tea in the Treetops podcast focusing on YA books, book events and Book to Movie Adaptations"
        },
        {
            "url": "http://armavir.yuginform.ru/catalog/searchresults?typeOfSearch=all&what=%D1%88%D0%BA%D0%B0%D1%84%D1%8B",
            "imageUrl": "http://armavir.yuginform.ru/media/ban/1425024607.jpg",
            "title": "Шкафы — Армавир",
            "description": "Шкафы — Армавир Если вас не устраивают результаты поиска, попробуйте изменить запрос, убрать неключевые слова из поискового запроса и/или оптимизируйте вид поиска. Первое слово в запросе самое важное "
        }
    ];

    getLinks(currentIndex, batchSize) {
        return Promise.resolve(this.links.slice(currentIndex, currentIndex + batchSize));
    }
}
