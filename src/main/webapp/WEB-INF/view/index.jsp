<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ include file="/WEB-INF/view/layout/header.jsp"%>

<div class="container mt-5">
	<a href="/board/saveForm" style="color: white"><button
			class="btn btn-primary">글쓰기</button></a>
	<table class="table mt-5">
		<thead>
			<tr>
				<th scope="col">번호</th>
				<th scope="col">제목</th>
				<th scope="col">내용</th>
				<th scope="col">작성자</th>
				<th scope="col"></th>
				<th scope="col"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="board" items="${boardList}">
				<tr>
					<th>${board.id}</th>
					<td>${board.author}</td>
					<td>${board.title}</td>
					<td>${board.content}</td>
					<td><a href="/board/${board.id}/updateForm"><button
								class="btn btn-primary">수정</button></a></td>
					<td><a href="/board/${board.id}/delete"><button
								class="btn btn-danger">삭제</button></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<!-- 1. 페이지번호 처리 -->
	<nav aria-label="Page navigation example">
		<ul class="pagination">
			<c:forEach var="num" begin="${startPage}" end="${endPage}">
				<li class="page-item"><a href="/?page=${num - 1}&size=3"
					class="page-link">${num}</a></li>
			</c:forEach>
		</ul>
	</nav>
</div>

<%@ include file="/WEB-INF/view/layout/footer.jsp"%>