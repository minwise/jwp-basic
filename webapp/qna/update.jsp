<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="answerUpdate">
		<form action="/api/qna/modifyAnswer" name="answer" method="post">
			<input type="hidden" name="questionId" value="${answer.questionId}" />
			<input type="hidden" name="answerId" value="${answer.answerId}" />
			<div class="form-group col-lg-4" style="padding-top: 10px;">
				<input class="form-control" id="writer" name="writer"
					placeholder="이름" value="${answer.writer}">
			</div>
			<div class="form-group col-lg-12">
				<textarea name="contents" id="contents" class="form-control"
					placeholder=""></textarea>
			</div>
			<input class="btn btn-success pull-right" type="submit" value="답변하기" />
			<div class="clearfix" />
		</form>
	</div>
</body>
</html>