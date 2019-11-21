import pyodbc
from entryGen import populateDB

groupCount = 10
change = 1

conn = pyodbc.connect('Driver={SQL Server};'
                      'Server=DESKTOP-KG3BLBI\SQLEXPRESS;'
                      'Database=StackOverflow;'
                      'Trusted_Connection=yes;')

cursor = conn.cursor()

if change == 1:
    '''Erasing all the data from the database'''
    cursor.execute("EXEC sp_MSForEachTable 'DISABLE TRIGGER ALL ON ?'")
    cursor.execute("EXEC sp_MSForEachTable 'ALTER TABLE ? NOCHECK CONSTRAINT ALL'")
    cursor.execute("EXEC sp_MSForEachTable 'DELETE FROM ?'")
    cursor.execute("EXEC sp_MSForEachTable 'ALTER TABLE ? CHECK CONSTRAINT ALL'")
    cursor.execute("EXEC sp_MSForEachTable 'ENABLE TRIGGER ALL ON ?'")

    '''Group insertion'''
    for i in range(10):
        cursor.execute('INSERT INTO Groups VALUES (' + str(i+1) + ', 0)')

    '''Triggers for incrementing/decrementing group ppl count'''
    cursor.execute( 'CREATE OR ALTER TRIGGER insert_count ON Users '
                    'AFTER INSERT '
                    'AS ' 
                    'BEGIN '
                    'UPDATE Groups SET NoPpl = NoPpl + 1 FROM Inserted WHERE Groups.Gid = inserted.Gid; '
                    'END;' )

    cursor.execute( 'CREATE OR ALTER TRIGGER delete_count ON Users '
                    'AFTER DELETE '
                    'AS ' 
                    'BEGIN '
                    'UPDATE Groups SET NoPpl = NoPpl - 1 FROM Deleted WHERE Groups.Gid = deleted.Gid; '
                    'END;' )

    cursor.execute( 'CREATE OR ALTER TRIGGER update_count ON Users '
                    'AFTER UPDATE '
                    'AS ' 
                    'BEGIN '
                    'UPDATE Groups SET NoPpl = NoPpl - 1 FROM Deleted WHERE Groups.Gid = deleted.Gid; '
                    'END;' )

    populateDB(groupCount, cursor)

    conn.commit()

cursor.execute('SELECT * FROM Users')

for row in cursor:
    print(row)


