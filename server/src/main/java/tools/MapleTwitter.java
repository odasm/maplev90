/*
 * This file is part of the OdinMS MapleStory Private Server
 * Copyright (C) 2012 Patrick Huy and Matthias Butz
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 *
 * @author AuroX
 */
public class MapleTwitter {

    private static final String KEY = "c7a52055d08731fa682513b5eb8bf42f45c028fe";

    public static boolean sendGameFeed(final String message) {
        try {
            final StringBuilder url = new StringBuilder();
            url.append("http://www.tropikms.info/feed/twitter.php?key=").append(KEY);
            url.append("&msg=").append(URLEncoder.encode(message, "UTF-8"));

            final URL twitter = new URL(url.toString());
            final URLConnection tConnection = twitter.openConnection();
            final InputStream input = tConnection.getInputStream();
            final BufferedReader dataInput = new BufferedReader(new InputStreamReader(input));
            final String response = dataInput.readLine(); // imma read the first line
            if (response.equals("1")) {
                return true;
            }
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
            FileoutputUtil.log("TwitterFeed.rtf", "MalformedURLException has occured. Reason: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
            FileoutputUtil.log("TwitterFeed.rtf", "IOException has occured. Reason: " + ioe);
        }
        return false;
    }
}
