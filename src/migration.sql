CREATE TABLE `user` (
                        `idUser` int NOT NULL AUTO_INCREMENT,
                        `username` varchar(45) NOT NULL,
                        `password` varchar(255) NOT NULL,
                        PRIMARY KEY (`idUser`),
                        KEY `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `playlist` (
                            `idPlaylist` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(45) NOT NULL,
                            `owner` varchar(255) NOT NULL,
                            PRIMARY KEY (`idPlaylist`),
                            KEY `userToPL_idx` (`owner`),
                            CONSTRAINT `userTOPLay` FOREIGN KEY (`owner`) REFERENCES `user` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `token` (
                         `idToken` varchar(255) NOT NULL,
                         `username` varchar(255) DEFAULT NULL,
                         PRIMARY KEY (`idToken`),
                         UNIQUE KEY `idToken_UNIQUE` (`idToken`),
                         KEY `userToken_idx` (`username`),
                         CONSTRAINT `userToken` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `track` (
                         `idTrack` int NOT NULL AUTO_INCREMENT,
                         `title` varchar(45) NOT NULL,
                         `performer` varchar(45) NOT NULL,
                         `duration` int NOT NULL,
                         `album` varchar(45) DEFAULT NULL,
                         `playcount` int NOT NULL,
                         `publicationDate` date NOT NULL,
                         `description` varchar(255) DEFAULT NULL,
                         `offlineAvailable` bit(1) NOT NULL,
                         PRIMARY KEY (`idTrack`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `playlisttrack` (
                                 `idPlaylistTrack` int NOT NULL AUTO_INCREMENT,
                                 `trackId` int NOT NULL,
                                 `playlistId` int NOT NULL,
                                 PRIMARY KEY (`idPlaylistTrack`),
                                 KEY `trackToPlay_idx` (`trackId`),
                                 KEY `playToTrack_idx` (`playlistId`),
                                 CONSTRAINT `playToTrack` FOREIGN KEY (`playlistId`) REFERENCES `playlist` (`idPlaylist`),
                                 CONSTRAINT `trackToPlay` FOREIGN KEY (`trackId`) REFERENCES `track` (`idTrack`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
