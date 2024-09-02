	//모달창 열기
	document.getElementById('delButton').addEventListener('click',function(){
		document.getElementById('myModal').style.display='block';
	});
	
	//모달창 닫기
	document.getElementById('close').addEventListener('click', function(){
		document.getElementById('myModal').style.display='none';
	});
	
	//모달 창 바깥을 클릭 시 창 닫기
	window.onclick =function(event){
		const modal =document.getElementById('myModal');
		if(event.target==modal){
			modal.style.display ='none';
		}
	};
	
	// "회원탈퇴"라는 문구를 입력했을 때만 버튼 활성화
	document.addEventListener('input', function() {
	        const inputText = document.getElementById('checkText').value;
	        const deleteButton = document.getElementById('deleteButton');
	
	        if (inputText === '회원탈퇴') {
	            deleteButton.disabled = false;
	        } else {
				
	            deleteButton.disabled = true;
	        }
	    });
	
	    // 버튼 클릭 시 deleteAccount.do로 이동
	    document.getElementById('deleteButton').addEventListener('click', function() {
	        
			window.location.href = 'deleteAccount.do'; 
	    });
		
		
