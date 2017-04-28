/*grab_mail_max_sort*/
SELECT
	IFNULL(f.sort,0) sort
FROM
	t_grab_file f
WHERE
	f.parentId =@pid
ORDER BY f.sort LIMIT 0,1

/*grab_mail_file_level*/
SELECT
	COUNT(*)
FROM
	t_grab_mail m
WHERE
	m.theme =@theme
AND m.sender =@sender
AND m.addressee =@addressee
AND m.sendTime =@sendTime

/*grab_mail_count*/
SELECT
	f.id,
	f.mailId,
	f.parentId,
	f.folderName,
	f.fileName,
	f.url,
	f.fileSize,
	f.type,
	f.`status`,
	f.createTime,
	f.updateTime,
	f.`level`,
	f.fullPath,
	f.sort,
	f.groupType
FROM
	t_grab_file f
WHERE
	f.fileName =@fileName
AND f.parentId = @parentId

/*grab_mail_url*/
SELECT
	f.url
FROM
	t_grab_file f
$condition

/*grab_mail_download*/
SELECT
	f.id,
	f.parentId,
	f.url
FROM
	t_grab_file f
WHERE
	f.url IS NOT NULL
$condition

/*grab_mail_filepath*/
SELECT
	f.id,
	f.mailId,
	f.parentId,
	f.folderName,
	f.fileName,
	f.url,
	f.fileSize,
	f.type,
	f.`status`,
	f.createTime,
	f.updateTime,
	f.`level`,
	f.fullPath,
	f.sort,
	f.groupType
FROM
	t_grab_file f
WHERE
	id = (
		SELECT
			f.parentId
		FROM
			t_grab_file f
		WHERE
			id =@fileId
	)