/* Part 1 */
create table Hardware (
    SKU char(20) NOT NULL,
    price decimal(10,2),
    descr VARCHAR(200),
    aisle char(5),
    PRIMARY KEY (SKU)
);
create table Customer (
    CID integer NOT NULL,
    PRIMARY KEY (CID)
);
create table BuysHardware (
    SKU char(20) NOT NULL,
    CID integer NOT NULL,
    PRIMARY KEY (SKU, CID),
    FOREIGN KEY(SKU) references Hardware(SKU),
    FOREIGN KEY(CID) references Customer(CID)
 );
create table Contractor (
    licnum integer NOT NULL,
    bname varchar(200),
    discountpct decimal(10,2),
    CID integer NOT NULL,
    PRIMARY KEY (licnum),
    FOREIGN KEY(CID) references Customer(CID)
);
create table HomeOwner (
    ID integer NOT NULL,
    birthdate date,
    CID integer NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY(CID) references Customer(CID)
);
create table Plant (
    SKU char(20) NOT NULL,
    price decimal(10,2),
    descr varchar(200),
    needsun boolean,
    climate varchar(20),
    PRIMARY KEY (SKU)
);
create table BuysPlant (
    SKU char(20) NOT NULL,
    CID integer NOT NULL,
    PRIMARY KEY (SKU, CID),
    FOREIGN KEY(SKU) references Plant(SKU),
    FOREIGN KEY(CID) references Customer(CID)
);
create table Flower (
    SKU char(20) NOT NULL,
    color char(12),
    PRIMARY KEY (SKU),
    FOREIGN KEY(SKU) references Plant(SKU)
);
create table Grower (
  name char(20) NOT NULL,
  address varchar(100),
  PRIMARY KEY(name)
);
create table Sells (
    SKU char(20) NOT NULL,
    name char(20) NOT NULL,
    PRIMARY KEY(SKU, name),
    FOREIGN KEY(SKU) references Plant(SKU),
    FOREIGN KEY(name) references Grower(name)
);
INSERT INTO Hardware VALUES('ml4321', '23.45', 'Magnet Transformer', 'L4');
INSERT INTO Customer VALUES('333548');
INSERT INTO Customer VALUES('78562');
INSERT INTO Customer VALUES('61489');
INSERT INTO BuysHardware VALUES('ml4321','78562');
INSERT INTO Contractor VALUES('99437', 'Roger and Co', '.15', '333548');
INSERT INTO HomeOwner VALUES('66785', '1967-08-15', '61489');
INSERT INTO Plant VALUES('p84234','12.99', 'Standard Venus Flytrap', '0', 'Dry');
INSERT INTO Plant VALUES('pf8989','15.95', 'Dandelion', '1', 'Fields');
INSERT INTO BuysPlant VALUES('p84234','78562');
INSERT INTO Flower VALUES('pf8989','Yellow');
INSERT INTO Grower VALUES('Optimus Growers','989 Cybertron');
INSERT INTO Sells VALUES('pf8989', 'Optimus Growers');
INSERT INTO Sells VALUES('p84234', 'Optimus Growers');

/* Part 2 */
/* Total participation is enforced for
   Customers with Contractors/Homeowners
   by having NOT NULL tied to the CID
   Values.*/

/* Part 3 */
create trigger enforce_Hard_disjoint
before insert on Hardware
  for each row
    begin
      if new.SKU in (
        SELECT SKU
        FROM Plant
        )
        then signal sqlstate '45000'
          SET MESSAGE_TEXT = 'Subclasses must be disjoint';
        end if;
    end;

create trigger enforce_UHard_disjoint
before update on Hardware
  for each row
    begin
      if new.SKU in (
        SELECT SKU
        FROM Plant
        )
        then signal sqlstate '45000'
          SET MESSAGE_TEXT = 'Subclasses must be disjoint';
        end if;
    end;

create trigger enforce_Plant_disjoint
before insert on Plant
  for each row
    begin
      if new.SKU in (
        SELECT SKU
        FROM Hardware
        )
        then signal sqlstate '45000'
          SET MESSAGE_TEXT = 'Subclasses must be disjoint';
        end if;
    end;

create trigger enforce_UPlant_disjoint
before update on Plant
  for each row
    begin
      if new.SKU in (
        SELECT SKU
        FROM Hardware
        )
        then signal sqlstate '45000'
          SET MESSAGE_TEXT = 'Subclasses must be disjoint';
        end if;
    end;

/* Tests to insure Disjoint holds */
INSERT INTO Plant VALUES('ml4321','15.95', 'Dandelion', '1', 'Fields');
INSERT INTO Hardware VALUES('pf8989', '23.45', 'Magnet Transformer', 'L4');

UPDATE Plant
SET SKU = 'ml4321'
WHERE SKU = 'pf8989';

UPDATE Hardware
SET SKU = 'pf8989'
WHERE SKU = 'ml4321';


/* For Easy Removal of Tables */
DROP TABLE IF EXISTS Sells, Grower, Flower, BuysPlant,
    Plant, HomeOwner, Contractor, BuysHardware,
    Customer, Hardware;
