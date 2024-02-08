<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/view/layout/header.jsp"%>


<div class="mb-3 mt-5 container">
	<h1 class="mt-5">이곳은 저장하는 장소입니다.</h1>
	<form action="/board/save" method="post">
		<label for="author" class="form-label">author</label> <input
			type="text" class="form-control" id="author" placeholder="author"
			name="author"> <label for="title" class="form-label">title</label>
		<input type="text" class="form-control" id="title" placeholder="title"
			name="title"> <label for="content" class="form-label">content</label>
		<textarea class="form-control" id="content" name="content" rows="3"></textarea>
		<input type="submit" class="btn btn-primary mt-2" />
	</form>
</div>
<%@ include file="/WEB-INF/view/layout/footer.jsp"%>